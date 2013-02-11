package tictactoe.pack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class TicTacToeActivity extends Activity {
	 private Game game;
	    private Button[][] buttons = new Button[3][3];
	    private TableLayout layout;


	    public TicTacToeActivity() {
	        game = new Game();
	        game.start();
	    }
	    /**
	     * Called when the activity is first created.
	     */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        layout = (TableLayout) findViewById(R.id.main_l);
	        buildGameField();
	    }

	    private void buildGameField() {
	        Square[][] field = game.getField();
	        for (int i = 0, lenI = field.length; i < lenI; i++ ) {
	            TableRow row = new TableRow(this); // �������� ������ �������
	            for (int j = 0, lenJ = field[i].length; j < lenJ; j++) {
	                Button button = new Button(this);
	                buttons[i][j] = button;
	                button.setOnClickListener(new Listener(i, j)); // ��������� ���������, ������������ �� ���� �� ������
	                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
	                        TableRow.LayoutParams.WRAP_CONTENT)); // ���������� ������ � ������ �������
	                button.setWidth(107);
	                button.setHeight(107);
	            }
	            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
	                    TableLayout.LayoutParams.WRAP_CONTENT)); // ���������� ������ � �������
	        }
	    }

	    public class Listener implements View.OnClickListener {
	        private int x = 0;
	        private int y = 0;

	        public Listener(int x, int y) {
	            this.x = x;
	            this.y = y;
	        }

	        public void onClick(View view) {
	            Button button = (Button) view;
	            Game g = game;
	            Player player = g.getCurrentActivePlayer();
	            if (makeTurn(x, y)) {
	                button.setText(player.getName());
	            }
	            Player winner = g.checkWinner();
	            if (winner != null) {
	                gameOver(winner);
	            }
	            if (g.isFieldFilled()) {  // � ������, ���� ���� ���������
	                gameOver();
	            }
	        }
	    }

	    private boolean makeTurn(int x, int y) {
	        return game.makeTurn(x, y);
	    }

	    private void gameOver(Player player) {
	        CharSequence text = "Player \"" + player.getName() + "\" won!";
	        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	        game.reset();
	        refresh();
	    }

	    private void gameOver() {
	        CharSequence text = "Draw";
	        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	        game.reset();
	        refresh();
	    }

	    private void refresh() {
	        Square[][] field = game.getField();

	        for (int i = 0, len = field.length; i < len; i++) {
	            for (int j = 0, len2 = field[i].length; j < len2; j++) {
	                if (field[i][j].getPlayer() == null) {
	                    buttons[i][j].setText("");
	                } else {
	                    buttons[i][j].setText(field[i][j].getPlayer().getName());
	                }
	            }
	        }
	    }
	}