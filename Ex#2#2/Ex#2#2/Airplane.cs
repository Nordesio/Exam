using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Security.Cryptography.Pkcs;
using System.Text;
using System.Threading.Tasks;

namespace Ex_2_2
{
    public class Airplane
    {
        [Key]
        public int Id { get; set; }
        public int tailNumber { get; set; }
        public string Model { get; set; }
        
        public int AirportId { get; set; }

        public virtual Airport Airport { get; set; } = null;

    }
}
