package com.zrisan.my_finance.api;

import com.zrisan.my_finance.models.Account;
import com.zrisan.my_finance.models.AuthToken;
import com.zrisan.my_finance.models.Category;
import com.zrisan.my_finance.models.CategoryTotal;
import com.zrisan.my_finance.models.LoginRequest;
import com.zrisan.my_finance.models.Message;
import com.zrisan.my_finance.models.NewAccount;
import com.zrisan.my_finance.models.RegistrationRequest;
import com.zrisan.my_finance.models.TotalBalance;
import com.zrisan.my_finance.models.Transaction;
import com.zrisan.my_finance.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @FormUrlEncoded
    @POST("auth/login")
    Call<AuthToken> login(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("auth/register")
    Call<Message> register(@Field("username") String username, @Field("password") String password);

    @POST("auth/logout")
    Call<Void> logout();


    @GET("account")
    Call<List<Account>> obtenerCuentas();

    @GET("account/total")
    Call<TotalBalance> obtenerTotal();

    @GET("expense/all")
    Call<List<CategoryTotal>> obtenerTotalExpenses();

    @GET("income/all")
    Call<List<CategoryTotal>> obtenerTotalIncomes();

    @FormUrlEncoded
    @POST("account")
    Call<NewAccount> saveAccount(@Field("name") String name, @Field("balance") Double balance);

    @GET("transaction/{accountId}/{typeTrs}")
    Call<List<Transaction>> obtenerTransaccionesCuenta(@Path("accountId") int accountId, @Path("typeTrs") String typeTrs);

    @GET("expense")
    Call<List<Category>> obtenerCategoryExpense();

    @GET("income")
    Call<List<Category>> obtenerCategoryIncome();
    @FormUrlEncoded
    @POST("income")
    Call<Category> saveCategoryIncome(@Field("name") String name);

    @FormUrlEncoded
    @POST("expense")
    Call<Category> saveCategoryExpense(@Field("name") String name);

    @FormUrlEncoded
    @POST("transaction")
    Call<Transaction> saveTransaction(
            @Field("description") String description,
            @Field("amount") double amount,
            @Field("dateTransaction") String dateTransaction,
            @Field("typeTransaction") String typeTransaction,
            @Field("categoryId") int categoryId,
            @Field("accountId") int accountId
            );

}
