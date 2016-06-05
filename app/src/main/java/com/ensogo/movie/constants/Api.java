package com.ensogo.movie.constants;

public class Api
{
    public static final String KEY = "166762ee7ad0555065795de29c83a977";
    public static final String GET_POPULAR_MOVIES = "http://api.themoviedb.org/3/discover/movie?language=en&sort_by=popularity.desc&" + appendAPIkey();
    public static final String GET_HIGHEST_RATED_MOVIES = "http://api.themoviedb.org/3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc&" + appendAPIkey();
    public static final String GET_TRAILERS = "http://api.themoviedb.org/3/movie/%s/videos?" + appendAPIkey();
    public static final String GET_REVIEWS = "http://api.themoviedb.org/3/movie/%s/reviews?" + appendAPIkey();
    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w342";
    public static final String BACKDROP_PATH = "http://image.tmdb.org/t/p/w780";

    private static String appendAPIkey() {

        return Constants.API_KEY + "=" + KEY;
    }
}
