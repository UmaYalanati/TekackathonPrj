package com.tek.ems.services;

import com.tek.ems.model.BreakDetails;
import com.tek.ems.model.ChangePassword;
import com.tek.ems.model.CreateLeaveRequest;
import com.tek.ems.model.EmployeeDetails;
import com.tek.ems.model.InTakeMasterDetails;
import com.tek.ems.model.InsertBreakIn;
import com.tek.ems.model.InsertClockIn;
import com.tek.ems.model.LeaveCategorylist;
import com.tek.ems.model.LeaveDetails;
import com.tek.ems.model.Login;
import com.tek.ems.model.ScheduleTime;
import com.tek.ems.model.TimeSheetDetails;
import com.tek.ems.model.TimeSheetDetailsApprove;
import com.tek.ems.model.TimeSheetReport;
import com.tek.ems.model.UpcomingEvents;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by uyalanat on 24-10-2016.
 */
public interface EMSService {

    @POST("employees/update/{employeeId}")
    Call<EmployeeDetails> updateEmployee(@Path("employeeId") int employeeId,@Body EmployeeDetails employeeDetails);

    @GET("employees/{employeeId}")
    Call<EmployeeDetails> getEmployeeById(@Path("employeeId") int employeeId);

    @GET("employees/reportes{employeeId}")
    Call<EmployeeDetails> getreportes(@Path("employeeId") int employeeId);

    @GET("Leave/GetLeaveDetails")
    Call<List<LeaveDetails>> getLeaveDetails(@Query("employeeId") int employeeId, @Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo, @Query("LeaveStatusId") int leaveStatusId);

    @GET("employees/applyLeaves")
    Call<CreateLeaveRequest> createLeaveRequest(@Query("employeeId") int employeeId,@Query("attendanceMode") String attendanceMode,@Query("absenceCategory") String absenceCategory,@Query("leaveReason") String leaveReason,@Query("startDate") String startDate,@Query("endDate") String endDate);

    @GET("teksystems/login")
    Call<Login> getLogin(@Query("userName") String userName,@Query(value = "password", encoded = true) String password);

    @PUT("Login/ChangePassword")
    Call<ChangePassword> changePassword(@Body ChangePassword changePassword);

    @GET("TimeSheet/GetTimeSheetDetails")
    Call<List<TimeSheetDetails>> getTimeSheetDetails(@Query("employeeId") int employeeId, @Query("dateFrom") String dateFrom,@Query("dateTo") String dateTo,@Query("status") String status);

    @PUT("TimeSheet/UpdateTimeCardApproval")
    Call<TimeSheetDetailsApprove> updateTimeCardApproval(@Body TimeSheetDetailsApprove timeSheetDetails);

    @GET("InTakeMaster/GetInTakeMasterDetails")
    Call<InTakeMasterDetails> getInTakeMasterDetails();

    @GET("timeandexpense/checkIn-OutTimeSheet")
    Call<InsertClockIn> insertClockIn(@Query("id") int id,@Query("latitude") double latitude,@Query("longitude") double longitude,@Query("comments") String comments,@Query("attendanceMode") String attendanceMode,@Query("flag") int flag);

    @POST("TimeSheet/InsertBreakIn")
    Call<InsertBreakIn> InsertBreakIn(@Body InsertBreakIn insertBreakIn);


    @GET("TimeSheet/GetBreakDetails")
    Call<List<BreakDetails>> getBreakDetails(@Query("timeSheetId") int timeSheetId,@Query("employeeId") int employeeId);


    @GET("OrgManagement/GetUpcomingEvents")
    Call<List<UpcomingEvents>> getUpcomingEvents(@Query("selectedDate") String selectedDate);

    @GET("OrgManagement/GetScheduleTime")
    Call<ScheduleTime> getScheduleTime(@Query("employeeId") int employeeId,@Query("selectedDate") String selectedDate);

    @GET("TimeSheetReport/GetTimeSheetReport")
    Call<List<TimeSheetReport>> getTimeSheetReport(@Query("employeeId") int employeeId, @Query("selectedDate") String selectedDate);
    //

    @GET("leave/GetLeaveCategorylist")
    Call<LeaveCategorylist> getLeaveCategorylist(@Query("employeeId") int employeeId, @Query("dateFrom") String dateFrom,@Query("dateTo") String dateTo);

}
