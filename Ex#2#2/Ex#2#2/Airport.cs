using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ex_2_2
{
    public class Airport
    {
        public int Id { get; set; }
        public string Name { get; set; }
        [ForeignKey("AirportId")]
        public virtual List<Airplane> Airplanes { get; set; }
    }
}
