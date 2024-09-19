package com.luishenriqueaguiar.paperrockscissors;

import static com.luishenriqueaguiar.paperrockscissors.MainActivity.GameChoice.*;
import static com.luishenriqueaguiar.paperrockscissors.MainActivity.GameResult.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.*;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    enum GameResult { WIN, LOSE, TIE }

    enum GameChoice { ROCK, PAPER, SCISSORS }

    public void onImageClick(View view) {
        GameChoice computerChoice = generateComputerChoice();

        setImageAppResult(computerChoice);

        GameChoice playerChoice = generatePlayerChoice(view);

        GameResult result = checkWinner(playerChoice, computerChoice);

        String textResult = setTextResult(result);

        TextView viewResult = findViewById(R.id.result);
        viewResult.setText(textResult);
    }

    private void setImageAppResult(GameChoice computerChoice) {
        ImageView imageViewApp = findViewById(R.id.image_app);

        switch (computerChoice) {
            case ROCK -> imageViewApp.setImageResource(R.drawable.pedra);
            case PAPER -> imageViewApp.setImageResource(R.drawable.papel);
            case SCISSORS -> imageViewApp.setImageResource(R.drawable.tesoura);
        }
    }

    private String setTextResult(GameResult result) {
        return switch (result) {
            case WIN -> "You win.";
            case LOSE -> "Computer wins.";
            case TIE -> "Tie.";
        };
    }

    private GameChoice generatePlayerChoice(View view) {
        GameChoice playerChoice;
        int contentId = view.getId();

        if (contentId == R.id.paper) {
            playerChoice = PAPER;
        } else if (contentId == R.id.rock) {
            playerChoice = ROCK;
        } else {
            playerChoice = SCISSORS;
        }

        return playerChoice;
    }

    private GameChoice generateComputerChoice() {
        int randomNumber = new Random().nextInt(3);

        return switch (randomNumber) {
            case 0 -> ROCK;
            case 1 -> PAPER;
            case 2 -> SCISSORS;
            default -> throw new IllegalStateException("Unexpected value: " + randomNumber);
        };
    }

    private GameResult checkWinner(GameChoice playerChoice, GameChoice computerChoice) {
        return switch(playerChoice) {
            case ROCK -> switch (computerChoice) {
                case PAPER -> LOSE;
                case ROCK -> TIE;
                case SCISSORS -> WIN;
            };
            case PAPER -> switch (computerChoice) {
                case PAPER -> TIE;
                case ROCK -> WIN;
                case SCISSORS -> LOSE;
            };
            case SCISSORS -> switch (computerChoice) {
                case PAPER -> WIN;
                case ROCK -> LOSE;
                case SCISSORS -> TIE;
            };
        };
    }
}