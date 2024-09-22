document.addEventListener('DOMContentLoaded', function() {
    const $time = document.querySelector('time');
    const $p = document.querySelector('p');
    const $input = document.querySelector('input');
    const $game = document.querySelector('#game');
    const $results = document.querySelector('#results');
    const $cpm = $results.querySelector('#results-cpm');
    const $accuracy = $results.querySelector('#results-accuracy');
    const $button = document.querySelector('#reload-button');

    const INITIAL_TIME = 30;

    const instructions = document.getElementById('instructions');
    if (instructions) {
        instructions.classList.add('instructions');
    }

    let exercises = [];
    let currentTime = INITIAL_TIME;
    let intervalId = null;
    let gameStarted = false; 

    function loadExercises() {
        return fetch('ejercicios.txt')
            .then(response => response.text())
            .then(text => {
                exercises = text.replace(/\r\n/g, '\n').split(/\n\s*\n/);
            })
            .catch(error => console.error('Error al cargar ejercicios:', error));
    }

    function getRandomExercise() {
        const randomIndex = Math.floor(Math.random() * exercises.length);
        return exercises[randomIndex].trim();
    }

    function initGame() {
        $game.style.display = 'flex';
        $results.style.display = 'none';
        $input.value = '';

        const instructions = document.getElementById('instructions');
        if (instructions) {
            instructions.style.display = 'block';
        }

        const TEXT = getRandomExercise();
        const lines = TEXT.split('\n');
        currentTime = INITIAL_TIME;
        gameStarted = false;

        $time.textContent = currentTime;
        $p.innerHTML = lines.map(line => {
            const words = line.split(' ');
            return words.map(word => {
                const letters = word.split('');
                return `<word>${letters.map(letter => `<letter>${letter}</letter>`).join('')}</word>`;
            }).join(' ');
        }).join('<br>'); 

        const $firstWord = $p.querySelector('word');
        if ($firstWord) {
            $firstWord.classList.add('active');
            const $firstLetter = $firstWord.querySelector('letter');
            if ($firstLetter) {
                $firstLetter.classList.add('active');
            }
        }
    }

    function initEvents() {
        document.addEventListener('keydown', () => {
            $input.focus();
        });
        $input.addEventListener('keydown', onKeyDown);
        $input.addEventListener('keyup', onKeyUp);
        $button.addEventListener('click', initGame);
    }

    function startTimer() {
        intervalId = setInterval(() => {
            currentTime--;
            $time.textContent = currentTime;
            if (currentTime === 0) {
                clearInterval(intervalId);
                gameOver();
            }
        }, 1000);
    }

    function onKeyDown(event) {
        if (!gameStarted) {
            gameStarted = true;
            startTimer();
        }

        const $currentWord = $p.querySelector('word.active');
        if (!$currentWord) return;

        const $currentLetter = $currentWord.querySelector('letter.active');
        if (!$currentLetter) return;

        const { key } = event;

        if (key === 'Backspace') {
            let $prevWord = $currentWord.previousElementSibling;
            const $prevLetter = $currentLetter.previousElementSibling;
        
            if (!$prevWord && !$prevLetter) {
                event.preventDefault();
                return;
            }
        
            while ($prevWord && ($prevWord.tagName === 'BR' || $prevWord.innerText.trim() === '')) {
                $prevWord = $prevWord.previousElementSibling;
            }
        
            if ($currentLetter.innerText === '' && $prevWord && $prevWord.tagName === 'BR') {
                event.preventDefault();
                $p.removeChild($prevWord);
                $currentWord.classList.remove('active');
        
                const $lastWord = $p.querySelector('word:last-of-type');
                if ($lastWord) {
                    const $lastLetter = $lastWord.querySelector('letter:last-child');
                    $lastWord.classList.add('active');
                    $lastLetter.classList.add('active');
                }
                return;
            }
        
            const $wordMarked = $p.querySelector('word.marked');
            if ($wordMarked && !$prevLetter) {
                event.preventDefault();
                $prevWord.classList.remove('marked');
                $prevWord.classList.add('active');
        
                const $letterGo = $prevWord.querySelector('letter:last-child');
        
                $currentLetter.classList.remove('active');
                $letterGo.classList.add('active');
        
                $input.value = Array.from($prevWord.querySelectorAll('letter.correct, letter.incorrect')).map($el => {
                    return $el.classList.contains('correct') ? $el.innerText : '*';
                }).join('');
            }
        
            if ($currentWord && $currentWord.previousElementSibling && $currentWord.previousElementSibling.tagName === 'BR') {
                const $prevLineLastWord = $currentWord.previousElementSibling.previousElementSibling;
                if ($prevLineLastWord && $currentLetter === $currentWord.querySelector('letter:first-child')) {
                    event.preventDefault();
                    const $lastLetter = $prevLineLastWord.querySelector('letter:last-child');
                    $currentWord.classList.remove('active');
                    $currentLetter.classList.remove('active');
                    $prevLineLastWord.classList.add('active');
                    $lastLetter.classList.add('active');
                    $currentWord = $prevLineLastWord;
                    $currentLetter = $lastLetter;
                }
            }
        }

        if (key === 'Enter') {
            event.preventDefault();
        
            let $nextWord = $currentWord.nextElementSibling;
            while ($nextWord && $nextWord.tagName !== 'BR') {
                $nextWord = $nextWord.nextElementSibling;
            }
        
            if ($nextWord) {
                $nextWord = $nextWord.nextElementSibling;
                while ($nextWord && ($nextWord.innerText.trim() === '' || $nextWord.tagName === 'BR')) {
                    $nextWord = $nextWord.nextElementSibling;
                }
        
                if ($nextWord) {
                    const $nextLetter = $nextWord.querySelector('letter');
        
                    $currentWord.classList.remove('active', 'marked');
                    $currentLetter.classList.remove('active');
        
                    $nextWord.classList.add('active');
                    if ($nextLetter) {
                        $nextLetter.classList.add('active');
                    }
        
                    $input.value = '';
                }
            }
            return;
        }

        if (key === ' ') {
            event.preventDefault();

            const $nextWord = $currentWord.nextElementSibling;
            if ($nextWord && $nextWord.tagName !== 'BR') {
                const $nextLetter = $nextWord.querySelector('letter');

                $currentWord.classList.remove('active', 'marked');
                $currentLetter.classList.remove('active');

                $nextWord.classList.add('active');
                if ($nextLetter) $nextLetter.classList.add('active');
                $input.value = '';

                const MissedL = $currentWord.querySelectorAll('letter:not(.correct)').length > 0;

                const classToAdd = MissedL ? 'marked' : 'correct';
                $currentWord.classList.add(classToAdd);

                return;
            }
        }
    }

    function onKeyUp(event) {
        const $currentWord = $p.querySelector('word.active');
        if (!$currentWord) return;

        const $currentLetter = $currentWord.querySelector('letter.active');
        if (!$currentLetter) return;

        const currentWord = $currentWord.innerText.trim();
        $input.maxLength = currentWord.length;

        const $allLetters = $currentWord.querySelectorAll('letter');

        $allLetters.forEach($letter => $letter.classList.remove('correct', 'incorrect'));

        $input.value.split('').forEach((char, index) => {
            const $letter = $allLetters[index];
            const Check = currentWord[index];

            const isCorrect = char === Check;
            const letterClass = isCorrect ? 'correct' : 'incorrect';
            if ($letter) $letter.classList.add(letterClass);
        });

        $currentLetter.classList.remove('active', 'is-last');
        const inputLength = $input.value.length;
        const $nextLetter = $allLetters[inputLength];

        if ($nextLetter) {
            $nextLetter.classList.add('active');
        } else {
            $currentLetter.classList.add('active', 'is-last');
        }

        if (isLastWordCompleted($currentWord)){
            gameOver();
        }
    }

    function isLastWordCompleted($currentword) {
        const $lastWord = $p.querySelector('word:last-of-type');
        const totalLetters = $lastWord.querySelectorAll('letter').length;
        const writtenLetters = $lastWord.querySelectorAll('letter.correct, letter.incorrect').length;

        return $currentword === $lastWord && totalLetters === writtenLetters;
    }

    function gameOver() {
        clearInterval(intervalId);
        $game.style.display = 'none';
        $results.style.display = 'flex';

        const correctWords = $p.querySelectorAll('word.correct').length;
        const correctLetter = $p.querySelectorAll('letter.correct').length;
        const incorrectLetter = $p.querySelectorAll('letter.incorrect').length;

        const totalLetters = correctLetter + incorrectLetter;

        const accuracy = totalLetters > 0
            ? (correctLetter / totalLetters) * 100
            : 0;

        const cpm = correctLetter * 60 / (INITIAL_TIME - currentTime);
        $cpm.textContent = cpm;
        $accuracy.textContent = `${accuracy.toFixed(2)}%`;

        const instructions = document.getElementById('instructions');
        if (instructions) {
            instructions.style.display = 'none';
        }
    }

    loadExercises().then(() => {
        initGame();
        initEvents();
    });
});