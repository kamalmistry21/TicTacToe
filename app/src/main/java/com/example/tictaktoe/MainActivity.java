package com.example.tictaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    int turn = 0; // o is for crosses, 1 is for nots
    int[] gamestate = {2,2,2,2,2,2,2,2,2};
    int[][] winningPostions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    int numberOfTurns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeTurnImage();
    }

    //
    public void dropIn(View view)
    {
        ImageView counter = (ImageView) view;
        int userclick = Integer.parseInt(counter.getTag().toString());

        if(gamestate[userclick] == 2) //checking if the spot is still free
        {
            // If 0 then crosses go, if 1 then nots turn
            if (turn == 0) {
                counter.setImageResource(R.drawable.crosses);
                counter.setTranslationY(-1000f);

                gamestate[userclick] = turn; //storing the users number in the gamestate
                turn = 1; //changing turns
            }
            else {
                counter.setImageResource(R.drawable.nots);
                counter.setTranslationY(-1000f);

                gamestate[userclick] = 1;//storing the users number in the gamestate
                turn = 0; // changing turns
            }

            counter.animate().translationYBy(1000f).setDuration(200);
            numberOfTurns++;
            // Checking through all the winning sttes and see if the user has won
            for (int[] winningPos:winningPostions) {

                // going through the winning state and checking if all the number are all the same, if so then there is a winner
                if (gamestate[winningPos[0]] == gamestate[winningPos[1]] &&
                        gamestate[winningPos[1]] == gamestate[winningPos[2]] &&
                        gamestate[winningPos[0]] != 2)
                {
                    checkWinner(gamestate[winningPos[0]]);

                }
                else if (numberOfTurns == 9)
                {
                    checkWinner(2);
                }
            }
            changeTurnImage();
        }
    }


    // checking the winner
    public void checkWinner(int whoWonOrDraw) {
        // getting all the layouts needed
        View viewGameOver = findViewById(R.id.constraintLayout);
        View viewGridLayout = findViewById(R.id.gridLayoutBoard);
        View linearLayoutGameOver = viewGameOver.findViewById(R.id.linearLayoutGameOver);
        TextView txtViewWinner = (TextView) viewGameOver.findViewById(R.id.textViewWinner);
        ImageView imageViewWinner = (ImageView) viewGameOver.findViewById(R.id.imageViewWinner);

        linearLayoutGameOver.setVisibility(View.VISIBLE);
        viewGridLayout.setVisibility(View.INVISIBLE);

        if (whoWonOrDraw == 0) {
            txtViewWinner.setText("Crosses Win");
            imageViewWinner.setImageResource(R.drawable.crosses);
        }
        else if (whoWonOrDraw == 1) {
            txtViewWinner.setText("Nots Win");
            imageViewWinner.setImageResource(R.drawable.nots);
        }
        else if (whoWonOrDraw == 2)
        {
            txtViewWinner.setText("Draw");
            imageViewWinner.setImageResource(R.drawable.draw);
        }
    }

    // Showing whos turn it is on screen
    public void changeTurnImage()
    {
        View view = findViewById(R.id.constraintLayout);

        ImageView changeTurnImage = (ImageView) view.findViewById(R.id.imageViewTurn);
        if (turn == 0 ) {
            changeTurnImage.setImageResource(R.drawable.crosses);
        }
        else{
            changeTurnImage.setImageResource(R.drawable.nots);
        }
    }

   // resetting the game
    public void playAgain(View view)
    {
        View viewGameOver = findViewById(R.id.constraintLayout);
        androidx.gridlayout.widget.GridLayout viewGridLayout = findViewById(R.id.gridLayoutBoard);
        View linearLayoutGameOver = viewGameOver.findViewById(R.id.linearLayoutGameOver);

        linearLayoutGameOver.setVisibility(View.INVISIBLE);
        viewGridLayout.setVisibility(View.VISIBLE);
        numberOfTurns = 0;

        //resetting the gamestate
        for  (int i = 0; i < gamestate.length; i++ )
        {
            gamestate[i] = 2;
        }

        for (int i = 0; i < viewGridLayout.getChildCount(); i++)
        {
            ((ImageView) viewGridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}