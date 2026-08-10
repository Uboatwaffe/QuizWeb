const params = new URLSearchParams(window.location.search);

const score = Number(params.get("score"));
const maxScore = Number(params.get("maxScore"));

document.getElementById("score").innerHTML = score;
document.getElementById("out").innerHTML = maxScore;