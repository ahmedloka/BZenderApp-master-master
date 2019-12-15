package apps.pixel.bzender.Network;


import java.util.List;

import apps.pixel.bzender.Models.AddTenders.AddTinderPojo;
import apps.pixel.bzender.Models.AddTenders.AddTinderResponse;
import apps.pixel.bzender.Models.AddTenders.AllCitiesModel;
import apps.pixel.bzender.Models.AddTenders.TendersModelResponse;
import apps.pixel.bzender.Models.EditProfileResponse;
import apps.pixel.bzender.Models.FillDataCar;
import apps.pixel.bzender.Models.FillDataElectrical;
import apps.pixel.bzender.Models.ForgetPasswordBody;
import apps.pixel.bzender.Models.ForgetPasswordResponse;
import apps.pixel.bzender.Models.GetOffers;
import apps.pixel.bzender.Models.ResponsePackages;
import apps.pixel.bzender.Models.TendersDetails.TenderDetails;
import apps.pixel.bzender.Models.TendersDetails.electrical.TenderDetailsElectrical;
import apps.pixel.bzender.Models.VerifyBody;
import apps.pixel.bzender.Models.allTinders.car.AllTender;
import apps.pixel.bzender.Models.allTinders.electrical.AllTenderElectrical;
import apps.pixel.bzender.Models.bookCar.BookCarBody;
import apps.pixel.bzender.Models.bookCar.BookCarResponse;
import apps.pixel.bzender.Models.bookElectrical.BookElectricalBody;
import apps.pixel.bzender.Models.bookElectrical.BookElectricalResponse;
import apps.pixel.bzender.Models.bookTenderRealEsate.BookTenderRealEstateBody;
import apps.pixel.bzender.Models.bookTenderRealEsate.BookTenderRealEstateResponse;
import apps.pixel.bzender.Models.changePassword.ChangePasswordModel;
import apps.pixel.bzender.Models.chatList.AllUserMessage;
import apps.pixel.bzender.Models.complaint.ComplaintModel;
import apps.pixel.bzender.Models.complaint.ComplaintResponse;
import apps.pixel.bzender.Models.editProfile.EditProfileModel;
import apps.pixel.bzender.Models.getAllTendersRealEstate.AllTenderRealEstateResponse;
import apps.pixel.bzender.Models.getTenderRealEstate.GetTenderRealEstateResponse;
import apps.pixel.bzender.Models.home.getCategoryResponse;
import apps.pixel.bzender.Models.imgs.UploadCarIamge;
import apps.pixel.bzender.Models.imgs.UploadCarRequest;
import apps.pixel.bzender.Models.imgs.UploadElectricalImage;
import apps.pixel.bzender.Models.imgs.UploadElectricalRequest;
import apps.pixel.bzender.Models.login.loginRequestModel;
import apps.pixel.bzender.Models.login.loginResponse;
import apps.pixel.bzender.Models.metadataCar.MetaDataCar;
import apps.pixel.bzender.Models.my_tenders.MyBookingBody;
import apps.pixel.bzender.Models.my_tenders.MyTendersBody;
import apps.pixel.bzender.Models.notification.NotificationResponse;
import apps.pixel.bzender.Models.profile.ProfileModel;
import apps.pixel.bzender.Models.push.PushNotification;
import apps.pixel.bzender.Models.push.PushResponse;
import apps.pixel.bzender.Models.realState.AddTenderRealStateBody;
import apps.pixel.bzender.Models.realState.AddTenderResponse;
import apps.pixel.bzender.Models.signUp.CountryCodeResponse;
import apps.pixel.bzender.Models.signUp.SignUpRequest;
import apps.pixel.bzender.Models.signUp.signUpResponse;
import apps.pixel.bzender.Models.singleChat.SingleChatResponse;
import apps.pixel.bzender.Models.verify.bussniess.VerifyBussniess;
import apps.pixel.bzender.Models.verify.bussniess.VerifyBussniessResponse;
import apps.pixel.bzender.Models.verify.invidual.VerifyInvidual;
import apps.pixel.bzender.Models.verify.invidual.VerifyInvidualResponse;
import apps.pixel.bzender.activities.uploadRealEState.UploadRealEstateImageBody;
import apps.pixel.bzender.activities.uploadRealEState.UploadRealEstateResponse;
import apps.pixel.bzender.activities.webPaymnet.EmptyResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitInterface {

    @POST("api/Account/Login")
    Observable<loginResponse> login(@Body loginRequestModel loginRequestModel);

    @POST("api/Account/Register")
    Observable<signUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @GET("api/Category")
    Observable<List<getCategoryResponse>> getCategory();

    @GET("api/Category/GetCategory")
    Observable<List<TendersModelResponse>> getTenderByCategory(@Query("id") String id);

    @GET("api/GetTender")
    Observable<TenderDetails> getTenderCarDetails(@Query("id") String id);

    @GET("api/GetTender")
    Observable<TenderDetailsElectrical> getTenderElectricalDetails(@Query("id") String id);

    @GET("api/GetTender")
    Observable<GetTenderRealEstateResponse> getTenderRealEstate(@Query("id") String id);

    @POST("api/AddTender")
    Observable<AddTinderResponse> addTinder(@Body AddTinderPojo addTinderPojo);


    @GET("Api/City/GetAllCities")
    Observable<List<AllCitiesModel>> getAllCities();


    @POST("Api/Complaint/Addcomplaint")
    Observable<ComplaintResponse> sendComplaint(@Body ComplaintModel complaint);


    @GET("api/Profile")
    Observable<ProfileModel> getProfileData();

    @POST("/api/Profile/SaveToProfile")
    Observable<EditProfileResponse> editProfile(@Body EditProfileModel editProfileModel);


    @POST("api/Account/ChangePassword")
    Observable<ChangePasswordModel> changePassword(@Body ChangePasswordModel changePasswordModel);

    @GET("api/Country/GetAll")
    Observable<List<CountryCodeResponse>> getCountriesCode();

    @POST("api/PushTokens/AddToken")
    Observable<PushResponse> pushNotification(@Body PushNotification pushNotification);

    @GET("api/GetTenderByCategoryId")
    Observable<List<AllTender>> getAllTendersCar(@Query("categoryId") String catId);

    @GET("api/GetTenderByCategoryId")
    Observable<List<AllTenderElectrical>> getAllTendersElectrical(@Query("categoryId") String catId);

    @GET("api/GetTenderByCategoryId")
    Observable<List<AllTenderRealEstateResponse>> getAllTendersRealEstate(@Query("categoryId") String catId);

    @GET("api/GetMetaData")
    Observable<MetaDataCar> getMetaData();


    @POST("api/AddTenderCar")
    Observable<FillDataCar> fillDataCar(@Body FillDataCar fillDataCar);

    @POST("api/AddTenderElectrical")
    Observable<FillDataElectrical> fillDataElectrical(@Body FillDataElectrical fillDataCar);

    @GET("api/GetOffers")
    Observable<GetOffers> getAllImages();

    @POST("api/AddTenderCarBooking")
    Observable<BookCarResponse> bookCar(@Body BookCarBody bookCarBody);

    @POST("api/AddTenderElectricalBooking")
    Observable<BookElectricalResponse> bookElectrical(@Body BookElectricalBody bookCarBody);

    @POST("api/AddTenderCarBookingImage")
    Observable<UploadCarRequest> uploadCarImage(@Body UploadCarIamge uploadCarIamge);

    @POST("api/AddTenderElectricalBookingImage")
    Observable<UploadElectricalRequest> uploadElectricalImage(@Body UploadElectricalImage uploadElectricalImage);

    @POST("api/AddTenderRealstateBookingImage")
    Observable<UploadRealEstateResponse> uploadBookingRealEstate(@Body UploadRealEstateImageBody uploadRealEstateImageBody);

    @GET("api/GetBooking")
    Observable<MyBookingBody> getMyBooking();


    @GET("MyTenders")
    Observable<List<MyTendersBody>> getMyTender();

    @GET("api/Account/Verify")
    Observable<VerifyBody> verifyAcc(@Query("Vcode") String code, @Query("Email") String email, @Query("Password") String password);


    @POST("api/Account/forgetpassword")
    Observable<ForgetPasswordResponse> forgetPasword(@Body ForgetPasswordBody forgetPasswordBody);

    @GET("api/GetNotifications")
    Observable<NotificationResponse> getNotifications(@Query("Page") int number);

    @GET("api/getAllUserMessagesWithOthers")
    Observable<AllUserMessage> getAllUserChat();


    @GET("api/GetChatMessages")
    Call<SingleChatResponse> getChatChatRoomData(@Query("RoomId") int roomID, @Query("page") int page);

    @GET("api/packages")
    Observable<List<ResponsePackages>> getPackages();


    @POST("Payment/index")
    Observable<EmptyResponse> openUrl(@Query("packageId") String id);


    @POST("api/BusinessPersons")
    Observable<VerifyBussniessResponse> verifyBussniess(@Body VerifyBussniess verifyBussniess);

    @POST("api/VerefiedPersons")
    Observable<VerifyInvidualResponse> verifyInvidual(@Body VerifyInvidual verifyInvidual);


    @POST("api/AddTenderRealstate")
    Observable<AddTenderResponse> addTenderRealState(@Body AddTenderRealStateBody addTenderBody);


    @POST("api/AddTenderRealstateBooking")
    Observable<BookTenderRealEstateResponse> bookTenderRealEstate(@Body BookTenderRealEstateBody addTenderBody);

}