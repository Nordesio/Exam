namespace C__Algorithms
{
    internal class MergeSort
    {
        public List<int> Sort(List<int> list)
        {
            if (list.Count <= 1)
            {
                return list;
            }

            var middleIndex = GetMiddleIndex(list);
            var leftSide = Sort(list.Take(middleIndex).ToList());
            var rightSide = Sort(list.Skip(middleIndex).ToList());

            return Merge(leftSide, rightSide);
        }

        private int GetMiddleIndex(List<int> list)
        {
            return list.Count / 2;
        }

        private List<int> Merge(List<int> leftSide, List<int> rightSide)
        {
            var result = new List<int>();
            int leftIndex = 0, rightIndex = 0;

            while (leftIndex < leftSide.Count && rightIndex < rightSide.Count)
            {
                if (leftSide[leftIndex] <= rightSide[rightIndex])
                {
                    result.Add(leftSide[leftIndex]);
                    leftIndex++;
                }
                else
                {
                    result.Add(rightSide[rightIndex]);
                    rightIndex++;
                }
            }

            result.AddRange(leftSide.Skip(leftIndex));
            result.AddRange(rightSide.Skip(rightIndex));

            return result;
        }
    }
}