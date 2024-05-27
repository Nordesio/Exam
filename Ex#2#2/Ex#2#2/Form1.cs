namespace Ex_2_2
{
    public partial class Form1 : Form
    {
        public List<Airplane> airplanes;
        public Form1()
        {
            InitializeComponent();
        }

        private void SaveData(object sender, FormClosedEventArgs e)
        {
            JsonHelper.SaveData(airplanes);
        }
        private void FormLoad(object sender, EventArgs e)
        {
            airplanes = JsonHelper.LoadData();
            foreach (Airplane airplane in airplanes)
            {
                listView1.Items.Add($"{airplane.Model} - {airplane.tailNumber}");
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Airplane airplane = new Airplane
            {
                tailNumber = int.Parse(textBox1.Text),
                Model = textBox2.Text
            };
            airplanes.Add(airplane);

            var linqAirplaneNumber = from t in airplanes
                                     where t.tailNumber > 100
                                     orderby t.Model
                                     select t;

            var linqLambdaAirplane = airplanes.Where(t => t.tailNumber > 100).OrderBy(t => t.Model).ToList();
            listView1.Clear();
            foreach (var air in linqAirplaneNumber)
            {
                listView1.Items.Add($"{air.Model} - {air.tailNumber}");
            }

            //listView1.Clear();

            //foreach (Airplane ap in airplanes)
            //{
            //    listView1.Items.Add($"{ap.Model} - {ap.tailNumber}");
            //}
        }

        private void button3_Click(object sender, EventArgs e)
        {
            airplanes.Clear();
            listView1.Clear();
        }
    }
}