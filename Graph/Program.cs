using System;
using System.Collections.Generic;

class Graph
{
    private int Vertices;
    private List<int>[] adjList;

    public Graph(int v)
    {
        Vertices = v;
        adjList = new List<int>[v];
        for (int i = 0; i < v; ++i)
            adjList[i] = new List<int>();
    }

    public void AddEdge(int v, int w)
    {
        adjList[v].Add(w);
    }

    public void BFS(int startVertex)
    {
        bool[] visited = new bool[Vertices];
        Queue<int> queue = new Queue<int>();
        visited[startVertex] = true;
        queue.Enqueue(startVertex);

        while (queue.Count != 0)
        {
            startVertex = queue.Dequeue();
            Console.Write(startVertex + " ");

            foreach (int nextVertex in adjList[startVertex])
            {
                if (!visited[nextVertex])
                {
                    visited[nextVertex] = true;
                    queue.Enqueue(nextVertex);
                }
            }
        }
        Console.WriteLine();
    }

    public void DFS(int startVertex)
    {
        bool[] visited = new bool[Vertices];
        DFSUtil(startVertex, visited);
        Console.WriteLine();
    }

    private void DFSUtil(int v, bool[] visited)
    {
        visited[v] = true;
        Console.Write(v + " ");

        foreach (int nextVertex in adjList[v])
        {
            if (!visited[nextVertex])
            {
                DFSUtil(nextVertex, visited);
            }
        }
    }
}

class Program
{
    static void Main()
    {
        Graph g = new Graph(4);

        g.AddEdge(0, 1);
        g.AddEdge(0, 2);
        g.AddEdge(1, 2);
        g.AddEdge(2, 0);
        g.AddEdge(2, 3);
        g.AddEdge(3, 3);

        Console.WriteLine("Breadth First Traversal (starting from vertex 2):");
        g.BFS(2);

        Console.WriteLine("Depth First Traversal (starting from vertex 2):");
        g.DFS(2);
    }
}
