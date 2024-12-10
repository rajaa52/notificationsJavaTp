package ma.ensa.notificationcounter.classes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import ma.ensa.notificationcounter.MainActivity;
import ma.ensa.notificationcounter.R;

public class CounterNotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "counter_channel";

    @Override
    public void onReceive(Context context, Intent intent) {

        showNotifications(context);
    }

    public void showNotifications(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Création du channel pour Android 8.0 et plus
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Counter Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
        // Intent pour ouvrir l'app quand on clique sur la notification
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context,
                0,
                mainIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        // Construction de la notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Compteur")
                .setContentText("Le compteur a atteint 5!")
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        notificationManager.notify(1, builder.build());
}
}