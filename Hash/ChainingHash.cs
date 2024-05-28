using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HashChain
{
    public class ChainingHash<TKey, TValue>
    {
        private const int InitialCapacity = 16;
        private List<KeyValuePair<TKey, TValue>>[] table;

        public ChainingHash()
        {
            table = new List<KeyValuePair<TKey, TValue>>[InitialCapacity];
            for (int i = 0; i < table.Length; i++)
            {
                table[i] = new List<KeyValuePair<TKey, TValue>>();
            }
        }

        private int GetHash(TKey key)
        {
            return Math.Abs(key.GetHashCode()) % table.Length;
        }

        public void Add(TKey key, TValue value)
        {
            int hash = GetHash(key);
            var bucket = table[hash];
            foreach (var kvp in bucket)
            {
                if (kvp.Key.Equals(key))
                {
                    throw new ArgumentException("Key already exists");
                }
            }
            bucket.Add(new KeyValuePair<TKey, TValue>(key, value));
        }

        public bool TryGetValue(TKey key, out TValue value)
        {
            int hash = GetHash(key);
            var bucket = table[hash];
            foreach (var kvp in bucket)
            {
                if (kvp.Key.Equals(key))
                {
                    value = kvp.Value;
                    return true;
                }
            }
            value = default;
            return false;
        }

        public bool Remove(TKey key)
        {
            int hash = GetHash(key);
            var bucket = table[hash];
            for (int i = 0; i < bucket.Count; i++)
            {
                if (bucket[i].Key.Equals(key))
                {
                    bucket.RemoveAt(i);
                    return true;
                }
            }
            return false;
        }
    }

    class Program
    {
        static void Main()
        {
            var hashTable = new ChainingHash<string, int>();
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
