package com.ensogo.movie.details;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ensogo.movie.R;
import com.ensogo.movie.entities.Movie;
import com.ensogo.movie.entities.Review;
import com.ensogo.movie.entities.Video;
import com.ensogo.movie.favorites.FavoritesInteractor;
import com.ensogo.movie.favorites.IFavoritesInteractor;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailsPresenter implements IMovieDetailsPresenter
{
    private IMovieDetailsView mMovieDetailsView;
    private IMovieDetailsInteractor mMovieDetailsInteractor;
    private IFavoritesInteractor mFavoritesInteractor;
    private Activity mActivity;

    public MovieDetailsPresenter(Activity activity, IMovieDetailsView movieDetailsView)
    {
        mActivity = activity;
        mMovieDetailsView = movieDetailsView;
        mMovieDetailsInteractor = new MovieDetailsInteractor();
        mFavoritesInteractor = new FavoritesInteractor();
    }

    @Override
    public void showDetails(Movie movie)
    {
        mMovieDetailsView.showDetails(movie);
    }

    @Override
    public Subscription showTrailers(Movie movie)
    {
        return mMovieDetailsInteractor.getTrailers(movie.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Video>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(List<Video> videos)
                    {
                        mMovieDetailsView.showTrailers(videos);
                    }
                });
    }

    @Override
    public Subscription showReviews(Movie movie)
    {
        return mMovieDetailsInteractor.getReviews(movie.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Review>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(List<Review> reviews)
                    {
                        mMovieDetailsView.showReviews(reviews);
                    }
                });
    }

    @Override
    public void showFavoriteButton(Movie movie)
    {
        boolean isFavorite = mFavoritesInteractor.isFavorite(movie.getId());
        if(isFavorite)
        {
            mMovieDetailsView.showFavorited();
        } else
        {
            mMovieDetailsView.showUnFavorited();
        }
    }

    @Override
    public void onFavoriteClick(Movie movie)
    {
        boolean isFavorite = mFavoritesInteractor.isFavorite(movie.getId());
        if(isFavorite)
        {
            mFavoritesInteractor.unFavorite(movie.getId());
            mMovieDetailsView.showUnFavorited();
        } else
        {
            showFavoriteInputDialog(movie);
        }
    }

    private void showFavoriteInputDialog(final Movie movie)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(String.format(mActivity.getString(R.string.favorite_input_title), movie.getTitle()));

        final EditText input = new EditText(mActivity);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing here because we override this button later to change the close behaviour.
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String inputText = input.getText().toString();
                if(TextUtils.isEmpty(inputText)) {
                    showErrorDialog();
                }else {
                    dialog.dismiss();
                    movie.setFavoriteReason(inputText);
                    mFavoritesInteractor.setFavorite(movie);
                    mMovieDetailsView.showFavorited();
                }
            }
        });
    }

    private void showErrorDialog()
    {
        new AlertDialog.Builder(mActivity)
                .setTitle(mActivity.getString(R.string.error))
                .setMessage(mActivity.getString(R.string.favorite_input_error))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .show();
    }
}
