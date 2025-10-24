package msku.ceng.madlab.week5;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.logging.XMLFormatter;

public class MainActivity extends AppCompatActivity {
    static final String Player_1 = "X";
    static final String Player_2 = "O";
    boolean player1_turn= true;
    byte[][] board = new byte[3][3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.board), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TableLayout table =findViewById(R.id.board);
        for (int i=0; i<3; i++){
            TableRow tableRow = (TableRow) table.getChildAt(i);
            for (int j=0; j<3; j++){
                Button button = (Button) tableRow.getChildAt(j);
                button.setOnClickListener(new CellListener(i, j));

            }
        }
    }
    public boolean isValidMove(int row, int column){
        return board[row][column]==0;

    }
    public int gameEnded(int row, int col){
        int symbol = board[row][col];
        boolean win = true;
        for (int i=0; i<3; i++){
            if (board[i][col]!= symbol){
                win = false;
                break;
            }
        }
        if (win){
            return symbol;

        }

        win = true;
        for (int j=0; j<3; j++){
            if (board[row][j]!= symbol){
                win = false;
                break;
            }
        }
        if (win){
            return symbol;
        }



        if (row == col) {
            win = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][i] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return symbol;
            }
        }


        if (row + col == 2) {
            win = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][2 - i] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return symbol;
            }
        }

        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) break;
        }
        if (isDraw) {
            return 0;
        }



        return -1;
    }


        //check row+
    class CellListener implements View.OnClickListener {
        int row, column;

        public CellListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void onClick(View v) {
            if (!isValidMove(row, column)) {
                Toast.makeText(MainActivity.this, "Cell is already occupied", Toast.LENGTH_SHORT).show();
                return;

            }
            if (player1_turn) {
                ((Button) v).setText(Player_1);
                board[row][column] = 1;


            } else {
                ((Button) v).setText(Player_2);
                board[row][column] = 2;

            }
            if (gameEnded(row, column) == -1) {
                player1_turn = !player1_turn;

            } else if (gameEnded(row, column) == 0) {
                Toast.makeText(MainActivity.this, "It's a DRAW", Toast.LENGTH_LONG).show();
            } else if (gameEnded(row, column) == 1) {
                Toast.makeText(MainActivity.this, "Player 1 wins", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Player 2 wins", Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putBoolean("player1Turn", player1_turn);
        byte[] boardSingle = new byte[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardSingle[3 * i * j] = board[i][j];
            }
        }


        outState.putByteArray("board", boardSingle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        player1_turn = savedInstanceState.getBoolean("player1Turn");
        byte[] boardSingle = savedInstanceState.getByteArray("board");
        for(int i=0; i< 9 ; i++){
            board[i/3][i%3] = boardSingle[i];
        }

        TableLayout table = findViewById(R.id.board);
        for(int i=0; i<3 ; i++){
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j=0 ; j<3 ; j++) {
                Button button =(Button) row.getChildAt(j);
                if(board[i][j] == 1 ){
                    button.setText("X");
                } else if (board[i][j] == 2) {
                    button.setText("0");


                }
            }
        }

    }
}


