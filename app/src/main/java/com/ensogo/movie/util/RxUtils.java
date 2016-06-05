package com.ensogo.movie.util;

import rx.Subscription;

public class RxUtils
{
    public static void unsubscribe(Subscription subscription)
    {
        if (subscription != null)
        {
            if (!subscription.isUnsubscribed())
            {
                subscription.unsubscribe();
            } else
            {
                // Already unsubscribed
            }
        } else
        {
            // Subscription doesn't exist
        }
    }

}
