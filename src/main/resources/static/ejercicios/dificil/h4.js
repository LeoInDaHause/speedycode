document.addEventListener('DOMContentLoaded', function() {
    const $time = document.querySelector('time');
    const $p = document.querySelector('p');
    const $input = document.querySelector('input');
    const $game = document.querySelector('#game');
    const $results = document.querySelector('#results');
    const $difficulty = document.querySelector('#results-difficulty');
    const $cpm = $results.querySelector('#results-cpm');
    const $accuracy = $results.querySelector('#results-accuracy');
    const $timem = $results.querySelector('#results-time');
    const $button = document.querySelector('#reload-button');

    const DEFAULT_TIME = 30;
    let gameTime = DEFAULT_TIME;
    let gameStarted = false;

    const instructions = document.getElementById('instructions');
    if (instructions) {
        instructions.classList.add('instructions');
    }

    let exercises = [];
    let currentTime = gameTime;
    let intervalId = null;

    function loadExercises() {
        const basePath = '/ejercicios/dificil/';
        const files = ['4.txt'];
    
        const randomFile = basePath + files[Math.floor(Math.random() * files.length)];
        console.log('Fetching exercise from:', randomFile);
    
        return fetch(randomFile)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(text => {
                exercises = text.replace(/\r\n/g, '\n').split(/\n\s*\n/);
            })
            .catch(error => console.error('Error al cargar ejercicios:', error));
    }

    function initGame() {
        $game.style.display = 'flex';
        $results.style.display = 'none';
        $input.value = '';

        const instructions = document.getElementById('instructions');
        if (instructions) {
            instructions.style.display = 'block';
        }

        const TEXT = exercises[Math.floor(Math.random() * exercises.length)];
        const lines = TEXT.split('\n');
        currentTime = gameTime;
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

        document.addEventListener('touchstart', () => {
            $input.focus();
        });

        $input.addEventListener('keydown', onKeyDown);
        $input.addEventListener('input', onInput);
        $button.addEventListener('click', initGame);

        document.getElementById('15-button').addEventListener('click', () => updateGameTime(15));
        document.getElementById('30-button').addEventListener('click', () => updateGameTime(30));
        document.getElementById('60-button').addEventListener('click', () => updateGameTime(60));
        document.getElementById('90-button').addEventListener('click', () => updateGameTime(90));
    }

    function updateGameTime(seconds) {
        if (!gameStarted) {
            gameTime = seconds;
            currentTime = gameTime;
            $time.textContent = currentTime;
        }
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
            handleBackspace($currentWord, $currentLetter, event);
        } else if (key === 'Enter') {
            handleEnter($currentWord, $currentLetter, event);
        } else if (key === ' ') {
            handleSpace($currentWord, $currentLetter, event);
        }
    }

    function handleBackspace($currentWord, $currentLetter, event) {
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
            }
        }
        if ($input.value === '') {
            $currentWord.classList.remove('correct', 'incorrect');
        }
        if ($currentWord.innerText.length === 1) {
            $currentWord.classList.remove('correct', 'incorrect');
            $currentLetter.classList.remove('correct', 'incorrect');
        }
    }

    function handleEnter($currentWord, $currentLetter, event) {
        event.preventDefault();

        const $lastLetter = $currentWord.lastElementChild;
        const $lastWordInLine = $currentWord.nextElementSibling && $currentWord.nextElementSibling.tagName === 'BR';

        if ($currentLetter !== $lastLetter || !$lastWordInLine) {
            return;
        }

        const currentWordText = Array.from($currentWord.querySelectorAll('letter')).map($letter => $letter.innerText).join('');
        const inputWordText = $input.value.trim();

        if (currentWordText === inputWordText) {
            $currentWord.classList.add('correct');
        } else {
            $currentWord.classList.add('incorrect');
        }

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
    }

    function handleSpace($currentWord, $currentLetter, event) {
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
        }
    }

    function onInput(event) {
        const $currentWord = $p.querySelector('word.active');
        if (!$currentWord) return;

        const $currentLetter = $currentWord.querySelector('letter.active');
        if (!$currentLetter) return;

        const currentWord = $currentWord.innerText.trim();
        $input.maxLength = currentWord.length;

        const $allLetters = $currentWord.querySelectorAll('letter');

        requestAnimationFrame(() => {
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

            if ($input.value === '') {
                $currentWord.classList.remove('correct', 'incorrect');
            }

            if (isLastWordCompleted($currentWord)){
                gameOver();
            }
        });
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

        const difficulty = "Hard";
        const correctLetters = $p.querySelectorAll('letter.correct').length;
        const incorrectLetters = $p.querySelectorAll('letter.incorrect').length;
        const totalLetters = $p.querySelectorAll('letter').length;
        const skippedLetters = totalLetters - (correctLetters + incorrectLetters);

        const accuracy = totalLetters > 0
            ? (correctLetters / totalLetters) * 100
            : 0;

        $difficulty.textContent = difficulty;
        const cpm = correctLetters * 60 / (gameTime - currentTime);
        $cpm.textContent = cpm.toFixed(2);
        $accuracy.textContent = `${accuracy.toFixed(2)}%`;

        document.getElementById('correct-characters').textContent = correctLetters;
        document.getElementById('incorrect-characters').textContent = incorrectLetters;
        document.getElementById('skipped-characters').textContent = skippedLetters;
        document.getElementById('total-characters').textContent = totalLetters;

        const timem = gameTime - currentTime;
        $timem.textContent = timem;

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