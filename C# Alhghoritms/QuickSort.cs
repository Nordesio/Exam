namespace C__Alhghoritms
{
    public class QuickSort
    {
        private int GetMiddleIndex(List<int> list)
        {
            return list.Count / 2;
        }

        public List<int> Sort(List<int> list)
        {
            if (list.Count <= 1)
                return list;

            var middleIndex = GetMiddleIndex(list);
            var middle = list[middleIndex];

            var leftSide = new List<int>();
            var rightSide = new List<int>();
            var middleSide = new List<int>( middle);

            foreach (var item in list)
            {
                if (item < middle)
                {
                    leftSide.Add(item);
                }
                else if(item > middle) 
                {
                    rightSide.Add(item);
                }
                else
                {
                    middleSide.Add(item);
                }
            }

            List<int> result = new List<int>();

            leftSide = Sort(leftSide);
            rightSide = Sort(rightSide);

            result.AddRange(leftSide);
            result.AddRange(middleSide);
            result.AddRange(rightSide);

            return result;
        }
    }
}
