using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HashDouble
{
    internal class DoubleHash<TKey, TValue>
    {
        private const int InitialCapacity = 16;
        private TKey[] keys;
        private TValue[] values;
        private int count;

        public DoubleHash()
        {
            keys = new TKey[InitialCapacity];
            values = new TValue[InitialCapacity];
        }

        private int GetPrimaryHash(TKey key)
        {
            return Math.Abs(key.GetHashCode()) % keys.Length;
        }

        private int GetSecondaryHash(TKey key)
        {
            return 1 + Math.Abs(key.GetHashCode()) % (keys.Length - 1);
        }

        private void Resize()
        {
            var oldKeys = keys;
            var oldValues = values;
            keys = new TKey[keys.Length * 2];
            values = new TValue[values.Length * 2];
            count = 0;

            for (int i = 0; i < oldKeys.Length; i++)
            {
                if (oldKeys[i] != null)
                {
                    Add(oldKeys[i], oldValues[i]);
                }
            }
        }

        public void Add(TKey key, TValue value)
        {
            if (count >= keys.Length / 2)
            {
                Resize();
            }

            int hash1 = GetPrimaryHash(key);
            int hash2 = GetSecondaryHash(key);
            int i = 0;

            while (keys[(hash1 + i * hash2) % keys.Length] != null)
            {
                if (keys[(hash1 + i * hash2) % keys.Length].Equals(key))
                {
                    throw new ArgumentException("Key already exists");
                }
                i++;
            }

            keys[(hash1 + i * hash2) % keys.Length] = key;
            values[(hash1 + i * hash2) % keys.Length] = value;
            count++;
        }

        public bool TryGetValue(TKey key, out TValue value)
        {
            int hash1 = GetPrimaryHash(key);
            int hash2 = GetSecondaryHash(key);
            int i = 0;

            while (keys[(hash1 + i * hash2) % keys.Length] != null)
            {
                if (keys[(hash1 + i * hash2) % keys.Length].Equals(key))
                {
                    value = values[(hash1 + i * hash2) % keys.Length];
                    return true;
                }
                i++;
            }

            value = default;
            return false;
        }

        public bool Remove(TKey key)
        {
            int hash1 = GetPrimaryHash(key);
            int hash2 = GetSecondaryHash(key);
            int i = 0;

            while (keys[(hash1 + i * hash2) % keys.Length] != null)
            {
                if (keys[(hash1 + i * hash2) % keys.Length].Equals(key))
                {
                    keys[(hash1 + i * hash2) % keys.Length] = default;
                    values[(hash1 + i * hash2) % keys.Length] = default;
                    count--;
                    return true;
                }
                i++;
            }
            return false;
        }
    }

    class Program
    {
        static void Main()
        {
            var hashTable = new DoubleHash<string, int>();
            hashTable.Add("one", 1);
            hashTable.Add("two", 2);
            hashTable.Add("three", 3);

            if (hashTable.TryGetValue("two", out int value))
            {
                Console.WriteLine("Value for 'two': " + value);
            }

            hashTable.Remove("two");

            if (!hashTable.TryGetValue("two", out _))
            {
                Console.WriteLine("'two' not found");
            }
        }
    }
}
