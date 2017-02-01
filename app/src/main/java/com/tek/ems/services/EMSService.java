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
import retrofit2.http.Query;

/**
 * Created by uyalanat on 24-10-2016.
 */
public interface EMSService {

    @PUT("Employee/UpdateEmployee")
    Call<EmployeeDetails> updateEmployee(@Body EmployeeDetails employeeDetails);

    @GET("Employee/GetEmployeeById")
    Call<EmployeeDetails> getEmployeeById(@Query("employeeId") int employeeId);

    @GET("Leave/GetLeaveDetails")
    Call<List<LeaveDetails>> getLeaveDetails(@Query("employeeId") int employeeId, @Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo, @Query("LeaveStatusId") int leaveStatusId);

    @POST("Leave/CreateLeaveRequest")
    Call<CreateLeaveRequest> createLeaveRequest(@Body CreateLeaveRequest createLeaveRequest);

    @GET("Login/GetLogin")
    Call<Login> getLogin(@Query("UserName") String userName,@Query("Password") String password);

    @PUT("Login/ChangePassword")
    Call<ChangePassword> changePassword(@Body ChangePassword changePassword);

    @GET("TimeSheet/GetTimeSheetDetails")
    Call<List<TimeSheetDetails>> getTimeSheetDetails(@Query("employeeId") int employeeId, @Query("dateFrom") String dateFrom,@Query("dateTo") String dateTo,@Query("status") String status);

    @PUT("TimeSheet/UpdateTimeCardApproval")
    Call<TimeSheetDetailsApprove> updateTimeCardApproval(@Body TimeSheetDetailsApprove timeSheetDetails);

    @GET("InTakeMaster/GetInTakeMasterDetails")
    Call<InTakeMasterDetails> getInTakeMasterDetails();

    @POST("TimeSheet/InsertClockIn")
    Call<InsertClockIn> insertClockIn(@Body InsertClockIn insertClockIn);

    @PUT("TimeSheet/UpdateClockOut")
    Call<InsertClockIn> updateClockOut(@Body InsertClockIn insertClockIn);

    @POST("TimeSheet/InsertBreakIn")
    Call<InsertBreakIn> InsertBreakIn(@Body InsertBreakIn insertBreakIn);

    @PUT("TimeSheet/UpdateBreakOut")
    Call<InsertBreakIn> updateBreakOut(@Body InsertBreakIn insertBreakIn);

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