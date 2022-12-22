package com.example.project4;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GameActivity  extends AppCompatActivity {
    Random random = new Random();
    RecyclerView gameBoard;
    Button continues, guess;
    TextView score1, score2, win, status1, status2;
    LinearLayoutManager layoutManager;
    HashMap<String, Integer> innerLoop = new HashMap<String, Integer>();
    HashMap<String, Integer> outerLoop = new HashMap<String, Integer>();
    HashMap<String, Integer> passed1 = new HashMap<String, Integer>(), passed2 = new HashMap<String, Integer>();
    int[] gopher = {random.nextInt(10), random.nextInt(10)};
    final static int X = 0, Y = 1;
    final int GUESSMODE = 0;
    final int CONTINUOUS = 1;
    int gameMode = CONTINUOUS;
    boolean lock = true, Player1Move = true;
    int counter1 = 1;
    int counter2 = 2;


    private final Handler player1Handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (lock) {
                if (what == 0) {
                    View view = layoutManager.findViewByPosition(gopher[Y]);
                    Button b = (Button) view.findViewById(R.id.b0 + gopher[X]);
                    b.setBackgroundColor(Color.DKGRAY);
                } else if (what == 1) {
                    View view = layoutManager.findViewByPosition(msg.arg2);
                    Button b = (Button) view.findViewById(R.id.b0 + msg.arg1);
                    b.setBackgroundColor(Color.WHITE);
                    score1.setText(Integer.toString(counter1));
                    counter1++;
                    if (innerLoop.containsKey(msg.arg1 + "," + msg.arg2)) {
                        status1.setText("1: Near Miss");
                    } else if (outerLoop.containsKey(msg.arg1 + "," + msg.arg2)) {
                        status1.setText("1: Close Call");
                    } else {
                        status1.setText("1: Miss");
                    }
                } else if (what == 2) {
                    View view = layoutManager.findViewByPosition(msg.arg2);
                    Button b = (Button) view.findViewById(R.id.b0 + msg.arg1);
                    b.setBackgroundColor(Color.RED);
                    win.setText("Player 1 Win");
                    status1.setText("1: Success");
                    lock = false;
                } else {
                    System.out.println("Confused");
                }
            }
        }
    };

    private final Handler player2Handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg) {
            int what = msg.what ;
            if (lock) {
                if (what == 1) {
                    View view = layoutManager.findViewByPosition(msg.arg2);
                    Button b = (Button) view.findViewById(R.id.b0 + msg.arg1);
                    b.setBackgroundColor(Color.BLACK);
                    score2.setText(Integer.toString(counter2));
                    counter2++;
                    if (innerLoop.containsKey(msg.arg1 + "," + msg.arg2)) {
                        status2.setText("2: Near Miss");
                    } else if (outerLoop.containsKey(msg.arg1 + "," + msg.arg2)) {
                        status2.setText("2: Close Call");
                    } else {
                        status2.setText("2: Miss");
                    }
                } else if (what == 2) {
                    View view = layoutManager.findViewByPosition(msg.arg2);
                    Button b = (Button) view.findViewById(R.id.b0 + msg.arg1);
                    b.setBackgroundColor(Color.RED);
                    win.setTextColor(Color.BLACK);
                    win.setText("Player 2 Win");
                    status2.setText("2: Success");
                    lock = false;
                } else {
                    System.out.println("Confused");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        gameBoard = (RecyclerView) findViewById(R.id.gameboard);
        MyAdapter adapter = new MyAdapter();
        gameBoard.setHasFixedSize(true);
        gameBoard.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        gameBoard.setLayoutManager(layoutManager);
        continues = (Button) findViewById(R.id.continues);
        guess = (Button) findViewById(R.id.guessByGuess);
        score1 = (TextView) findViewById(R.id.player1score);
        score2 = (TextView) findViewById(R.id.player2score);
        status1 = (TextView) findViewById(R.id.status1);
        status2 = (TextView) findViewById(R.id.status2);
        status2.setTextColor(Color.BLACK);
        win = (TextView) findViewById(R.id.winText);
        Thread t1 = new Thread(new Player1()) ;
        t1.start();
        Thread t2 = new Thread(new Player2()) ;
        t2.start();

        Loop();

        guess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gameMode != GUESSMODE) {
                    gameMode = GUESSMODE;
                    Toast.makeText(getApplicationContext(),"Guess-By-Guess Mode",Toast.LENGTH_SHORT).show();
                }
            }
        });

        continues.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gameMode != CONTINUOUS) {
                    gameMode = CONTINUOUS;
                    Toast.makeText(getApplicationContext(),"Continuous Mode",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Loop(){
        int I = gopher[X], J = gopher[Y];
        int x = I - 1;
        int y = J - 1;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (x == I && y == J) {
                    y++;
                    continue;
                }
                String temp1 = x + "," + y;
                innerLoop.put(temp1,0);
                y++;
            }
            y = J - 1;
            x++;
        }

        I = gopher[X];
        J = gopher[Y];
        x = I - 2;
        y = J - 2;
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 0 || i == 4) || (j == 0 || j == 4)) {
                    if (x == I && y == J) {
                        y++;
                        continue;
                    }
                    String temp1 = x + "," + y;
                    outerLoop.put(temp1,0);
                    y++;
                }
                y++;
            }
            y = J - 2;
            x++;
        }

    }


    public class Player1 implements Runnable {
        public void run() {
            try { Thread.sleep(1000); }
            catch (InterruptedException e) { System.out.println("Thread interrupted!") ; }
            Message msg = player1Handler.obtainMessage(0);
            player1Handler.sendMessage(msg);
            int x = 2, y = 2;
            while(lock) {
                if(gameMode == GUESSMODE) {
                    if (!Player1Move) {
                        continue;
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted!");
                        }
                        Player1Move = false;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                }
                if (passed2.containsKey(x + "," + y) || passed1.containsKey(x + "," + y)) {
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                    continue;
                }
                if (gopher[X] == x && y == gopher[Y]) { msg = player1Handler.obtainMessage(2); }
                else { msg = player1Handler.obtainMessage(1); }
                msg.arg1 = x;
                msg.arg2 = y;
                player1Handler.sendMessage(msg);
                passed1.put(x + "," + y, 0);
                x = random.nextInt(10);
                y = random.nextInt(10);
            }
        }
    }

    public class Player2 implements Runnable {
        public void run() {
            try { Thread.sleep(1500); }
            catch (InterruptedException e) { System.out.println("Thread interrupted!") ; }
            int x = 5, y = 5;
            Message msg;
            while(lock) {
                if(gameMode == GUESSMODE) {
                    if (Player1Move) {
                        continue;
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted!");
                        }
                        Player1Move = true;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                }
                if (passed1.containsKey(x + "," + y) || passed2.containsKey(x + "," + y)) {
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                    continue;
                }
                if (gopher[X] == x && y == gopher[Y]) { msg = player2Handler.obtainMessage(2); }
                else { msg = player2Handler.obtainMessage(1); }
                msg.arg1 = x;
                msg.arg2 = y;
                player2Handler.sendMessage(msg);
                passed1.put(x + "," + y, 0);
                x = random.nextInt(10);
                y = random.nextInt(10);
            }
        }
    }
}
