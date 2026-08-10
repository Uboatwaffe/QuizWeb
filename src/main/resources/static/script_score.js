const params = new URLSearchParams(window.location.search);


// fetching data from url
const score = Number(params.get("score"));
const maxScore = Number(params.get("maxScore"));

// displaying the score
document.getElementById("score").innerHTML = score.toString();
document.getElementById("out").innerHTML = maxScore.toString();