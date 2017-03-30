package com.moronkreacionz.tictactoe20170303;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // active player status : x (1) and zero (0)
    int activePlayer = 0;

    // String Winner
    String winnerName="";

    // 2 means the cell has not been played, 0 or 1 means that user has played the game
    int [] gameState = {2,2,2, 2,2,2, 2,2,2};

    // list of winning cell position combinations
    int [][] winningPositions = {
                                {0,1,2}, {3,4,5}, {6,7,8},    // horizontal
                                {0,3,6}, {1,4,7}, {2,5,8},    // vertical
                                {0,4,8}, {2,4,6}            // diagonal
                                };

    boolean gameIsActive = true;



    public void dropImageOnCell(View view){

        // bringing the item on the cell with animation for onClick event

        ImageView counter = (ImageView) view;
        // System.out.println(counter.getTag().toString());
        int currentTappedCell = Integer.parseInt(counter.getTag().toString());

        if(gameState[currentTappedCell] == 2 && gameIsActive ) {

            // assign cell position for current tapped position to the user who played it
            gameState[currentTappedCell] = activePlayer;

            // position the item off the screen layout
            counter.setTranslationY(-1000f);

            String strMessage ="";

            if (activePlayer == 0) {

                // select the image for user Zero
                counter.setImageResource(R.drawable.zero);

                // change, make next player as active user
                activePlayer = 1;

                // change message for user X
                strMessage = "X, you are next";

            } else {

                // select the image for user X
                counter.setImageResource(R.drawable.x);

                // change, make next player as active user
                activePlayer = 0;

                // change message for user Zero
                strMessage = "O, you are next";
            }

            // use animation sequence
            counter.animate().translationYBy(1000f).rotation(360).setDuration(500);

            for(int[] winningPosition : winningPositions) {

                //System.out.println("gameState[winningPosition[0]]"+gameState[winningPosition[0]]);
                //System.out.println("gameState[winningPosition[1]]"+gameState[winningPosition[1]]);
                //System.out.println("gameState[winningPosition[2]]"+gameState[winningPosition[2]]);

                if ( gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        &&
                         gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        &&
                         gameState[winningPosition[0]] != 2
                        )
                {
//                        (gameState[winningPosition[0]] == gameState[winningPosition[2]]) &&

                   // System.out.println("winningPosition[0]]" + winningPosition[0]);
                   // System.out.println("winningPosition[1]]" + winningPosition[1]);
                   // System.out.println("winningPosition[2]]" + winningPosition[2]);
                   // System.out.println("gameState[winningPosition[0]]" + gameState[winningPosition[0]]);
                   // System.out.println("gameState[winningPosition[1]]" + gameState[winningPosition[1]]);
                   // System.out.println("gameState[winningPosition[2]]" + gameState[winningPosition[2]]);

                    // game over - set game to inactive
                    gameIsActive = false;

                    winnerName = (gameState[winningPosition[0]] == 0) ? "0" : "X";
                    activePlayer = gameState[winningPosition[0]]; // retain winning user as active player

                    // someone has won // System.out.println(gameState[winningPosition[0]]);
                    strMessage = winnerName + " has won, game over!";

                    Button button = (Button) findViewById(R.id.playAgainButton);
                    button.setVisibility(View.VISIBLE);
                    break;
                } else {
                        boolean gameCompleted = true;
                        for(int checkCellStatus : gameState){
                            if(checkCellStatus==2) {
                                gameCompleted = false;
                            }
                        }
                        if(gameCompleted) {
                            strMessage = "Its a draw, lets restart game!";
                            GridLayout gridLayoutReset = (GridLayout) findViewById(R.id.gridLayout);
                            for (int i = 0; i < gridLayoutReset.getChildCount(); i++){
                                ((ImageView) gridLayoutReset.getChildAt(i)).setAlpha(0.3f);
                            }
                            Button button = (Button) findViewById(R.id.playAgainButton);
                            button.setVisibility(View.VISIBLE);
                            // game over - set game to inactive
                            gameIsActive = false;
                        }
                }
            }

            // update message instruction for next player
            TextView message = (TextView) findViewById(R.id.instructionMsgTextView);
            message.setText(strMessage);
        }
    }

    public void resetGame(View view){

        Button button = (Button) findViewById(R.id.playAgainButton);
        button.setVisibility(View.INVISIBLE);

        gameIsActive = true;

        for (int i = 0; i < gameState.length ; i++){
            gameState[i]=2;
        }

        GridLayout gridLayoutReset = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayoutReset.getChildCount(); i++){
            ((ImageView) gridLayoutReset.getChildAt(i)).setImageResource(0);
            ((ImageView) gridLayoutReset.getChildAt(i)).setAlpha(1f);
        }

        // update message instruction for next player
        TextView message = (TextView) findViewById(R.id.instructionMsgTextView);
        message.setText("Lets start with "+winnerName);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
