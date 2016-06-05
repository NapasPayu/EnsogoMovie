package com.ensogo.movie.listing;

import com.ensogo.movie.entities.Movie;

import java.util.List;

public interface IMoviesListingView
{
    void showMovies(List<Movie> movies);
    void loadingStarted();
    void loadingFailed(String errorMessage);
    void onMovieClicked(Movie movie);
    void onFavoriteInfoClicked(String movieTitle, String favoriteMessage);
}
