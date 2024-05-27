namespace C__Algorithms
{
    internal class TreeSort
    {
        public List<int> Sort(List<int> list)
        {
            var tree = new BTree(list[0]); 

            // Добавляем все элементы списка в дерево
            for (var i = 1; i < list.Count; i++)
            {
                tree.Add(list[i]);
            }

            var sortedList = new List<int>();
            tree.InOrderTraversal(tree.Root, sortedList);

            return sortedList;
        }

        // Определение класса Node
        public class Node(int value)
        {
            public int Value { get; set; } = value;
            public Node? LeftNode { get; set; }
            public Node? RightNode { get; set; }
        }

        // Определение класса BTree
        public class BTree(int root)
        {
            public Node Root { get; set; } = new(root);

            // Метод для добавления нового значения в дерево
            public void Add(int newValue)
            {
                var current = Root;

                while (true)
                {
                    if (newValue < current.Value)
                    {
                        if (current.LeftNode == null)
                        {
                            current.LeftNode = new Node(newValue);
                            break;
                        }

                        current = current.LeftNode;
                    }
                    else // newValue >= current.Value
                    {
                        if (current.RightNode == null)
                        {
                            current.RightNode = new Node(newValue);
                            break;
                        }

                        current = current.RightNode;
                    }
                }
            }

            // Метод для обхода дерева в порядке возрастания (левый, корень, правый)
            public void InOrderTraversal(Node? node, ICollection<int> sortedList)
            {
                if (node == null)
                    return;

                InOrderTraversal(node.LeftNode, sortedList);
                sortedList.Add(node.Value);
                InOrderTraversal(node.RightNode, sortedList);
            }
        }
    }
}
