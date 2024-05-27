using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
namespace Ex_2_2
{
    public class DBContext : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (optionsBuilder.IsConfigured == false)
            {
                optionsBuilder.UseSqlServer("localhost;");
            }

            base.OnConfiguring(optionsBuilder);
        }

        public virtual DbSet<Airplane> Airplanes { get; set; }
        public virtual DbSet<Airport> Airports { get; set; }

    }
}
