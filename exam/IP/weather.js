//alert("test")
document.getElementById('WeatherForm')
    .addEventListener('submit', async e => {
        e.preventDefault();
        const formData = new FormData(e.target)
        const a = formData.get('weather')
        let wther = a.split('/')
        const response = await fetch("https://vm.nathoro.ru/weather?lattitude=" + wther[0] + "&longitude=" + wther[1])
        const data = await response.json();
        console.info(data);
        const wrapper = document.getElementById('weather_cards');

        data.forEach(element => {
            render_weather(element, wrapper);
        });
    });

var render_weather = (data, wrapper) => {
    const cards = document.createElement('inner_cards');
    let date2 = new Date(data.date);
    cards.innerHTML = `<div class="inner_cards">Город: ${data.location}, Дата: ${date2.toLocaleDateString('ru-RU')}, 
    Температура воздуха: ${data.temperature.toFixed(2)}, Направление ветра: ${data.windDirection}, Скорость ветра: ${data.windSpeed.toFixed(2)}, Влажность воздуха: ${data.humidity.toFixed(2)}</div>`;
    wrapper.appendChild(cards);
}