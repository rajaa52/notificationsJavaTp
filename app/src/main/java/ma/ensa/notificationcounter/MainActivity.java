package ma.ensa.notificationcounter;

import android.content.Intent;
import android.app.PendingIntent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import ma.ensa.notificationcounter.classes.CounterNotificationReceiver;

public class MainActivity extends AppCompatActivity {
    private int counter = 0;
    private TextView counterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counterText = findViewById(R.id.counterText);
        Button incrementButton = findViewById(R.id.incrementButton);
        Button ResetButton = findViewById(R.id.ResetButton);
        incrementButton.setOnClickListener(v -> {
            counter++;
            updateCounterDisplay();
            if (counter == 5) {
                try {
                    notifyCounter();
                } catch (PendingIntent.CanceledException e) {
                    throw new RuntimeException(e);
                }

            }
        } );
        ResetButton.setOnClickListener(v -> {
            counter=0;
            updateCounterDisplay();
                }
        );
    }

    private void updateCounterDisplay() {
        counterText.setText(String.valueOf(counter));
    }

    private void notifyCounter() throws PendingIntent.CanceledException {
        // On envoie juste un broadcast, le Receiver s'occupe de la notification
        Intent intent = new Intent(this, CounterNotificationReceiver.class);
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE).send();
    }


}