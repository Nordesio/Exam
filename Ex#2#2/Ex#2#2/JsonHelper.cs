using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Ex_2_2
{
    public class JsonHelper
    {
        private static readonly string filePath = "Json.json";

        public static List<Airplane> LoadData()
        {
            if(File.Exists(filePath)) { 
                string json = File.ReadAllText(filePath);
                return JsonSerializer.Deserialize<List<Airplane>>(json) ?? new List<Airplane>();
            }
            return new List<Airplane>();
        }

        public static void SaveData(List<Airplane> airplanes)
        {
            string json = JsonSerializer.Serialize(airplanes);
            File.WriteAllText(filePath, json);
        }

    }
}
