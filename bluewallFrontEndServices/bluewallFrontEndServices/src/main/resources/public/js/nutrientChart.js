var chart;
var chartingOptions = {

    chart: {
        type: 'solidgauge',
        marginTop: 50
    },

    title: {
        text: '',
        style: {
            fontSize: '24px'
        }
    },

    tooltip: {
        borderWidth: 0,
        backgroundColor: 'none',
        shadow: false,
        style: {
            fontSize: '16px'
        },
        pointFormat: '{series.name}<br><span style="font-size:2em; color:{point.color}; font-weight: bold">{point.y}%</span>',
        positioner: function (labelWidth, labelHeight, point) {
            var tooltipX, tooltipY;
            
            tooltipX = point.plotX + chart.plotLeft - 40;
            tooltipY = point.plotY + chart.plotTop - 160;
            
            return {
                x: tooltipX,
                y: tooltipY
            };
        }
    },

    pane: {
        startAngle: 0,
        endAngle: 360,
        background: [{ // Track for Move
            outerRadius: '112%',
            innerRadius: '88%',
            backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0.3).get(),
            borderWidth: 0
        }, { // Track for Exercise
            outerRadius: '87%',
            innerRadius: '63%',
            backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[1]).setOpacity(0.3).get(),
            borderWidth: 0
        }, { // Track for Stand
            outerRadius: '62%',
            innerRadius: '38%',
            backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[2]).setOpacity(0.3).get(),
            borderWidth: 0
        }]
    },

    yAxis: {
        min: 0,
        max: 100,
        lineWidth: 0,
        tickPositions: []
    },

    plotOptions: {
        solidgauge: {
            borderWidth: '30px',
            dataLabels: {
                enabled: false
            },
            linecap: 'round',
            stickyTracking: false
        }
    },

    series: [{
        name: 'Proteins',
        borderColor: Highcharts.getOptions().colors[0],
        data: [{
            color: Highcharts.getOptions().colors[0],
            radius: '100%',
            innerRadius: '100%',
            y: this.protein
        }]
    }, {
        name: 'Carbs',
        borderColor: Highcharts.getOptions().colors[1],
        data: [{
            color: Highcharts.getOptions().colors[1],
            radius: '75%',
            innerRadius: '75%',
            y: this.carbs
        }]
    }, {
        name: 'Fats',
        borderColor: Highcharts.getOptions().colors[2],
        data: [{
            color: Highcharts.getOptions().colors[2],
            radius: '50%',
            innerRadius: '50%',
            y: this.fat
        }]
    }]
};