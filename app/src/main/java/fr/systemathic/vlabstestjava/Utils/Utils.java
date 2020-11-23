package fr.systemathic.vlabstestjava.Utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.systemathic.vlabstestjava.R;

public class Utils {

    public static void displayImageWithGlide(Context context, ImageView imageView,String pUrl){
        RequestOptions requestOptions = RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24).timeout(15000)
                .error(R.color.black);

        GlideUrl url = new GlideUrl(pUrl, new LazyHeaders.Builder()
                .addHeader("User-Agent", "user-agent")
                .build());

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions).load(url)
                .placeholder(R.drawable.ic_baseline_image_24).dontAnimate().into(imageView);
    }

    public static void showFailureRequestMessage(Context context,  String errorMessage){
        Toast.makeText(context, context.getString(R.string.error) + " : " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
