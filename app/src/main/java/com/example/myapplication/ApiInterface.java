package com.example.myapplication;

import com.example.myapplication.model.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //@GET("/todos")
   //Call<List<Todo>> getTodos();

     @GET("/video")
     Call<List<Todo>> getTodos();


    @GET("/todos/{id}")
    Call<Todo>getTodo(@Path("id") int id);

    @GET("/todos")
    Call<List<Todo>> getTodoUsingQuery(@Query("userId") int userId,@Query("completed") boolean completed);

    @POST("/todos")
    Call<Todo> postTodos(@Body Todo todo);
}