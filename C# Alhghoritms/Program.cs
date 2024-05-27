using System;
using System.Collections.Generic;
using C__Algorithms;

namespace C__Alhghoritms
{
    internal class Program
    {
        static void Main(string[] args)
        {
            // Создаем экземпляр класса Random для генерации случайных чисел
            Random rand = new Random();

            // Количество элементов в списке
            var count = 100;

            // Создаем новый список для каждой сортировки
            var list = GenerateRandomList(rand, count);

            // Сортировка пузырьком
            var bubbleSort = new BubbleSort();
            var bubbleSortedList = bubbleSort.Sort(new List<int>(list));
            PrintList(bubbleSortedList);

            // Создаем новый список для каждой сортировки
            list = GenerateRandomList(rand, count);

            // Сортировка слиянием
            var mergeSort = new MergeSort();
            var mergeSortedList = mergeSort.Sort(new List<int>(list));
            PrintList(mergeSortedList);

            // Создаем новый список для каждой сортировки
            list = GenerateRandomList(rand, count);

            // Быстрая сортировка
            var quickSort = new QuickSort();
            var quickSortedList = quickSort.Sort(new List<int>(list));
            PrintList(quickSortedList);

            // Создаем новый список для каждой сортировки
            list = GenerateRandomList(rand, count);

            var btreeSort = new TreeSort();
            var btreeSortList = btreeSort.Sort(new List<int>(list));
            PrintList(btreeSortList);
        }

        // Метод для генерации нового списка случайных чисел
        static List<int> GenerateRandomList(Random rand, int count)
        {
            var list = new List<int>();
            for (int i = 0; i < count; i++)
            {
                list.Add(rand.Next(0, 100));
            }
            return list;
        }

        // Метод для вывода списка на консоль
        static void PrintList(List<int> sortedList)
        {
            foreach (var item in sortedList)
            {
                Console.Write(item + " ");
            }
            Console.WriteLine(); // Переход на новую строку после вывода всех элементов
        }
    }
}