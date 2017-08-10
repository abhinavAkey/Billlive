package com.beatus.billlive.encryption;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class HashFactory 
{
	private static HashMap<String,Hash> registry = new HashMap<>();
	
	static 
	{
        registry.put("HmacSHA256", new MacHash("HmacSHA256"));
        registry.put("M:1", new RegularHash("SHA-256"));
		registry.put("X", new HMACSHA1HashedPassword());
	}

	
	public static Hash getInstance(String name)
	{
		Hash instance;
		
		instance = registry.get(name);
		if (instance == null)
		{
            throw new NoSuchElementException(String.format(
				"Algorithm name \"%s\" does not exist", 
				name));
		}
		
		return instance;
	}
	
	public interface Hash
	{
		public byte[] hash(byte[] key, byte[] data);
		public boolean match(byte[] key, byte[] data, byte[] hashData);
		public byte[] key();
        public int keySize();
	}

    public static class RegularHash implements Hash
    {
        private MessageDigest md;
        private int size;

        private RegularHash(String algorithm)
        {
            try {

                this.md = MessageDigest.getInstance(algorithm);

            } catch (Exception e) {

                throw new IllegalStateException(String.format(
                        "Execution failure: %s",
                        e.getMessage()),
                        e);
            }

            this.size = md.getDigestLength();
        }

        @Override
        public byte[] hash(byte[] key, byte[] data)
        {
            return performHash(key, data);
        }

        @Override
        public boolean match(byte[] key, byte[] data, byte[] hashData)
        {
            byte[] digest;

            if (hashData.length != this.size)
            {
                throw new IllegalArgumentException(String.format(
                        "Input data has wrong length for \"%s\" hash (%d != %d)",
                        this.md.getAlgorithm(),
                        hashData.length,
                        this.size));
            }

            digest = performHash(key, data);

            return Arrays.equals(hashData, digest);
        }

        @Override
        public byte[] key()
        {
            byte[] key;

            key = new byte[this.size];
            new SecureRandom().nextBytes(key);

            return key;
        }

        @Override
        public int keySize()
        {
            return this.size;
        }

        private byte[] performHash(byte[] key, byte[] data)
        {
            byte[] digest;

            try {

                this.md.reset();
                this.md.update(data);
                this.md.update(key);
                digest = this.md.digest();

            } catch (Exception e) {

                throw new IllegalStateException(String.format(
                        "Execution failure: %s",
                        e.getMessage()),
                        e);
            }

            return digest;
        }
    }

    public static class MacHash implements Hash
    {
        private Mac mac;
        private int size;

        private MacHash(String algorithm)
        {
            try {

                this.mac = Mac.getInstance(algorithm);

            } catch (Exception e) {

                throw new IllegalStateException(String.format(
                        "Execution failure: %s",
                        e.getMessage()),
                        e);
            }

            this.size = this.mac.getMacLength();
        }

        @Override
        public byte[] hash(byte[] key, byte[] data)
        {
            return performHash(key, data);
        }

        @Override
        public boolean match(byte[] key, byte[] data, byte[] hashData)
        {
            byte[] digest;

            if (hashData.length != this.size)
            {
                throw new IllegalArgumentException(String.format(
                        "Input data has wrong length for \"%s\" hash (%d != %d)",
                        this.mac.getAlgorithm(),
                        hashData.length,
                        this.size));
            }

            digest = performHash(key, data);

            return Arrays.equals(hashData, digest);

        }

        @Override
        public byte[] key()
        {
            byte[] key;

            key = new byte[this.size];
            new SecureRandom().nextBytes(key);

            return key;
        }

        @Override
        public int keySize()
        {
            return this.size;
        }

        private byte[] performHash(byte[] key, byte[] data)
        {
            SecretKeySpec keyspec;
            byte[] digest;

            try {

                keyspec = new SecretKeySpec(key, mac.getAlgorithm());
                mac.init(keyspec);
                digest = mac.doFinal(data);

            } catch (Exception e) {

                throw new IllegalStateException(String.format(
                        "Execution failure: %s",
                        e.getMessage()),
                        e);
            }

            return digest;
        }

    }

    public static class HMACSHA1HashedPassword implements Hash
	{
		private HMACSHA1HashedPassword()
		{
		}
		
		@Override
		public byte[] hash(byte[] key, byte[] data)
		{
			throw new UnsupportedOperationException(
			    "Creating an HMAC/SHA1 hash is no longer supported");
		}

		@Override
		public boolean match(byte[] key, byte[] data, byte[] hashData)
		{
			byte[] username;
			int len;
			byte[] oldhash;
			byte[] newhash;
			SecretKeySpec keyspec;
			Mac mac;
			
			if (hashData.length <= 20)
			{
				throw new IllegalArgumentException(String.format(
					"Input data has wrong length for hash and salt (%d <= 20)", 
					hashData.length));
			}
			
			len = hashData.length - 20;
			oldhash = new byte[20];
			System.arraycopy(hashData, 0, oldhash, 0, 20);
			username = new byte[len];
			System.arraycopy(hashData, 20, username, 0, len);
			
		    try {
		    	
			    keyspec = new SecretKeySpec(username, "HmacSHA1");
                mac = Mac.getInstance("HmacSHA1");
		        mac.init(keyspec);
		        newhash = mac.doFinal(data);
		        
            } catch (Exception e) {
            	
				throw new IllegalStateException(String.format(
					"Execution failure: %s", 
					e.getMessage()), 
					e);
			}

			return Arrays.equals(oldhash, newhash);
		}

		@Override
		public byte[] key() 
		{
			throw new UnsupportedOperationException(
				"Creating an HMAC/SHA1 hash key is not supported");
		}

        @Override
        public int keySize() {

            throw new UnsupportedOperationException(
         				"Creating an HMAC/SHA1 hash keySize is not supported");
        }
    }
}
