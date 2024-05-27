//alert("test")
document.getElementById('TimeForm')
.addEventListener('submit', async e => {
    e.preventDefault();
    const formData = new FormData(e.target)

    const response = await fetch("https://vm.nathoro.ru/timetable/by-room/" + encodeURIComponent(formData.get('time')))
    const data = await response.json();
    console.info(data);
    const wrapper = document.getElementById('timetable');

    data.forEach(element => {
        render_weather(element, wrapper);
    });
});

var render_weather = (data, wrapper) => {
    const tbl = document.createElement("table")
    tbl.innerHTML = `
    <tr>
        <th>1<th>
        <th>2<th>
        <th>3<th>
        <th>4<th>
        <th>5<th>
        <th>6<th>
        <th>7<th>
        <th>8<th>
    `;
    data.days.forEach(day => {
        const week = document.createElement('tr');
        day.lessons.forEach(lesson => {
            if(lesson === null){
                week.innerHTML += `<th>-<th>`;
            }else{
                week.innerHTML += `<th>${lesson.group.name} - ${lesson.subject.teacher.fullName} - ${lesson.subject.name} - ${lesson.subject.type}<th>`
            }
        });
        tbl.append(week);
    });


    
    wrapper.append(tbl);
    //wrapper.append(JSON.stringify(data));
}