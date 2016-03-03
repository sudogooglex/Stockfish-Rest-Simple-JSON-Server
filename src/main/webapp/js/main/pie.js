window.onload = function () {
    var chart = new CanvasJS.Chart("chartContainer",
            {
                title: {
                    text: "Time Statistics"
                },
                exportFileName: "Time Statistics",
                exportEnabled: true,
                animationEnabled: true,
                legend: {
                    verticalAlign: "bottom",
                    horizontalAlign: "center"
                },
                data: [
                    {
                        type: "pie",
                        showInLegend: true,
                        toolTipContent: "{legendText}: <strong>{y}%</strong>",
                        indexLabel: "{label} {y}%",
                        dataPoints: [
                            {y: 587, legendText: "time1Init", exploded: true, label: "time1Init"},
                            {y: 136, legendText: "time2UpdateNextBestMoove", label: "time2UpdateNextBestMoove"},
                            {y: 0, legendText: "time3UpdateBoardState", label: "time3UpdateBoardState"},
                            {y: 120, legendText: "time4UpdateEvaluationScore", label: "time4UpdateEvaluationScore"},
                            {y: 2389, legendText: "Other", label: "Other"}
                        ]
                    }
                ]
            });
    chart.render();
};