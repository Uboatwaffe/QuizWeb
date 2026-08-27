document.getElementById('resetBtn').addEventListener('click', () => {
    document.querySelectorAll('.answerButton.selected').forEach(button => {
        button.classList.remove('selected');
    });
});


document.querySelectorAll('.question').forEach(question => {

    const buttons = question.querySelectorAll('.answerButton');

    buttons.forEach(button => {
        button.addEventListener('click', () => {

            // Remove selected from all buttons in this question
            buttons.forEach(btn => {
                btn.classList.remove('selected');
            });

            // Select the clicked button
            button.classList.add('selected');
        });
    });
});