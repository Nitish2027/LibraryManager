package com.example.nitish2027.alpranlibrarymanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentSignUpActivity extends AppCompatActivity {

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText etsid, etfname, etlname, etpass, etrepass, etgender, etdob, etsdmsnumber, etplacement, etbatch, etssc;
    private EditText etaddress, etpin, etaadhar, etemail, etcnumber, etjrole, etedulevel;
    private ProgressDialog progressDialog;


    String strsid, strfname, strlname, strpass, strrepass, strgender, strdob;
    String strsdmsnumber, strplacement, strbatch, strssc;
    String straddress, strpin, straadhar, stremail, strcnumber, strjrole, stredulevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        Button btnsignup = (Button) findViewById(R.id.signup);
        etsid = (EditText) findViewById(R.id.esid);
        etfname = (EditText) findViewById(R.id.efname);
        etlname = (EditText) findViewById(R.id.elname);
        etpass = (EditText) findViewById(R.id.epass);
        etrepass = (EditText) findViewById(R.id.repass);
        etsdmsnumber = (EditText) findViewById(R.id.esdmsnumber);
        etaddress = (EditText) findViewById(R.id.eaddress);
        etpin = (EditText) findViewById(R.id.epin);
        etaadhar = (EditText) findViewById(R.id.eaadhar);
        etemail = (EditText) findViewById(R.id.email);
        etcnumber = (EditText) findViewById(R.id.ecnumber);

        progressDialog = new ProgressDialog(this);

        //Validation
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean Valid = SignUp_Validation();

                if(Valid) {

                    final String str_sid = etsid.getText().toString().trim();
                    final String str_fname = etfname.getText().toString().trim();
                    final String str_lname = etlname.getText().toString().trim();
                    final String str_pass = etpass.getText().toString().trim();
                    final String str_gender = etgender.getText().toString().trim();
                    final String str_dob = etdob.getText().toString().trim();
                    final String str_sdmsnumber = etsdmsnumber.getText().toString().trim();
                    final String str_placement = etplacement.getText().toString().trim();
                    final String str_batch = etbatch.getText().toString().trim();
                    final String str_ssc = etssc.getText().toString().trim();
                    final String str_address = etaddress.getText().toString().trim();
                    final String str_pin = etpin.getText().toString().trim();
                    final String str_aadhar = etaadhar.getText().toString().trim();
                    final String str_email = etemail.getText().toString().trim();
                    final String str_cnumber = etcnumber.getText().toString().trim();
                    final String str_jrole = etjrole.getText().toString().trim();
                    final String str_edulevel = etedulevel.getText().toString().trim();

                    progressDialog.setMessage("Registering User..");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            Constants.STUDENT_REGISTER,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                                StudentSignUpActivity.this);

                                        builder.setMessage(jsonObject.getString("message"));
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Okay",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog,
                                                                        int which) {
                                                        Intent i = new Intent(StudentSignUpActivity.this, MainActivity.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                });

                                        AlertDialog alert = builder.create();
                                        alert.show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.hide();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(
                                            StudentSignUpActivity.this);

                                    builder.setMessage(error.getMessage() + "! Server Not Working Properly. Try Again Later!");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("Okay",null);

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();

                            params.put("studentid",str_sid);
                            params.put("fname",str_fname);
                            params.put("lname",str_lname);
                            params.put("pass",str_pass);
                            params.put("gender",str_gender);
                            params.put("dob",str_dob);
                            params.put("sdms_number",str_sdmsnumber);
                            params.put("placement_status",str_placement);
                            params.put("batch",str_batch);
                            params.put("ssc",str_ssc);
                            params.put("address",str_address);
                            params.put("pin_code",str_pin);
                            params.put("aadhar_number",str_aadhar);
                            params.put("email_id",str_email);
                            params.put("contact_number",str_cnumber);
                            params.put("job_role",str_jrole);
                            params.put("education_level",str_edulevel);

                            return params;
                        }
                    };

                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                }
            }
        });

        //DOB
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        etdob = (EditText) findViewById(R.id.edob);
        etdob.setInputType(InputType.TYPE_NULL);
        etdob.requestFocus();
        setDateTimeField();

        //Gender
        etgender = (EditText) findViewById(R.id.egender);
        etgender.setInputType(InputType.TYPE_NULL);
        etgender.requestFocus();
        setGender();

        //Placement Status
        etplacement = (EditText) findViewById(R.id.eplacement);
        etplacement.setInputType(InputType.TYPE_NULL);
        etplacement.requestFocus();
        setPlacement();

        //Batch
        etbatch = (EditText) findViewById(R.id.ebatch);
        etbatch.setInputType(InputType.TYPE_NULL);
        etbatch.requestFocus();
        setBatch();

        //Sector Skill Counsil
        etssc = (EditText) findViewById(R.id.essc);
        etssc.setInputType(InputType.TYPE_NULL);
        etssc.requestFocus();
        setSsc();

        //Job Role
        etjrole = (EditText) findViewById(R.id.ejrole);
        etjrole.setInputType(InputType.TYPE_NULL);
        etjrole.requestFocus();
        setJrole();

        //Education Level
        etedulevel = (EditText) findViewById(R.id.edulevel);
        etedulevel.setInputType(InputType.TYPE_NULL);
        etedulevel.requestFocus();
        setEdulevel();
    }

    //Date Of Birth Picker
    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etdob.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        etdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == etdob) {
                    fromDatePickerDialog.show();
                }
            }
        });
    }

    //Gender
    private void setGender() {

        etgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        StudentSignUpActivity.this);

                CharSequence[] items = {"Male", "Female", "Other"};
                builder.setTitle("Select Your Gender:");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                etgender.setText("Male");
                                break;
                            case 1:
                                etgender.setText("Female");
                                break;
                            case 2:
                                etgender.setText("Other");
                                break;
                        }
                    }
                });

                if (view == etgender) {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }


    //Placement Status
    private void setPlacement() {

        etplacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        StudentSignUpActivity.this);

                CharSequence[] items = {"Yes", "No"};
                builder.setTitle("Placement Status:");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                etplacement.setText("Yes");
                                break;
                            case 1:
                                etplacement.setText("No");
                                break;
                        }
                    }
                });

                if (view == etplacement) {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }


    //Batch
    private void setBatch() {

        etbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        StudentSignUpActivity.this);

                CharSequence[] items = {"Batch 1", "Batch 2", "Batch 3"};
                builder.setTitle("Select Your Batch:");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                etbatch.setText("Batch 1");
                                break;
                            case 1:
                                etbatch.setText("Batch 2");
                                break;
                            case 2:
                                etbatch.setText("Batch 3");
                                break;
                        }
                    }
                });

                if (view == etbatch) {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }


    //SSC
    private void setSsc() {

        etssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        StudentSignUpActivity.this);

                CharSequence[] items = {"IT/ IT eS", "BFSI", "Management"};
                builder.setTitle("Select Your SSC:");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                etssc.setText("IT/ IT eS");
                                break;
                            case 1:
                                etssc.setText("BFSI");
                                break;
                            case 2:
                                etssc.setText("Management");
                                break;
                        }
                    }
                });

                if (view == etssc) {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }


    //Job Role
    private void setJrole() {

        etjrole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        StudentSignUpActivity.this);

                CharSequence[] items = {"ITSD", "DDEO", "JSD"};
                builder.setTitle("Select Your Job Role:");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                etjrole.setText("ITSD");
                                break;
                            case 1:
                                etjrole.setText("DDEO");
                                break;
                            case 2:
                                etjrole.setText("JSD");
                                break;
                        }
                    }
                });

                if (view == etjrole) {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }


    //Education Level
    private void setEdulevel() {

        etedulevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        StudentSignUpActivity.this);

                CharSequence[] items = {"10th", "12th", "Graduation", "Post-Graduation"};
                builder.setTitle("Education Level:");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                etedulevel.setText("10th");
                                break;
                            case 1:
                                etedulevel.setText("12th");
                                break;
                            case 2:
                                etedulevel.setText("Graduation");
                                break;
                            case 3:
                                etedulevel.setText("Post-Graduation");
                                break;
                        }
                    }
                });

                if (view == etedulevel) {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    public boolean SignUp_Validation(){

        final String str_sid = etsid.getText().toString();
        final String str_fname = etfname.getText().toString();
        final String str_lname = etlname.getText().toString();
        final String str_pass = etpass.getText().toString();
        final String str_repass = etrepass.getText().toString();
        final String str_gender = etgender.getText().toString();
        final String str_dob = etdob.getText().toString();
        final String str_sdmsnumber = etsdmsnumber.getText().toString();
        final String str_placement = etplacement.getText().toString();
        final String str_batch = etbatch.getText().toString();
        final String str_ssc = etssc.getText().toString();
        final String str_address = etaddress.getText().toString();
        final String str_pin = etpin.getText().toString();
        final String str_aadhar = etaadhar.getText().toString();
        final String str_email = etemail.getText().toString();
        final String str_cnumber = etcnumber.getText().toString();
        final String str_jrole = etjrole.getText().toString();
        final String str_edulevel = etedulevel.getText().toString();


        if(str_sid.length()<1) {
            etsid.setError("Student Id Required");
            strsid="no";
        }
        else
            strsid="yes";

        if(str_fname.length()<1) {
            etfname.setError("First Name Required");
            strfname="no";
        }
        else
            strfname="yes";

        if(str_lname.length()<1) {
            etlname.setError("Last Name Required");
            strlname="no";
        }
        else
            strlname="yes";

        if(str_pass.length()<1) {
            etpass.setError("Password Required");
            strpass="no";
        }
        else
            strpass="yes";

        if(!str_repass.equals(str_pass)) {
            etrepass.setError("Passwords Must Be Same");
            strrepass="no";
        }
        else
            strrepass="yes";

        if(str_gender.equals("Select")) {
            etgender.setError("Select Gender");
            strgender="no";
        }
        else if(!str_gender.equals("Select")){
            etgender.setError(null);
            strgender="yes";
        }
        else
            strgender="yes";

        if(str_dob.equals("Select")) {
            etdob.setError("Select Your DOB");
            strdob="no";
        }
        else if(!str_dob.equals("Select")) {
            etdob.setError(null);
            strdob="yes";
        }
        else
            strdob="yes";

        if(str_sdmsnumber.length()<1){
            etsdmsnumber.setError("SDMS Number Required");
            strsdmsnumber="no";
        }
        else
            strsdmsnumber="yes";

        if(str_placement.equals("Select")) {
            etplacement.setError("Select Your Placement Status");
            strplacement="no";
        }
        else if(!str_placement.equals("Select")) {
            etplacement.setError(null);
            strplacement="yes";
        }
        else
            strplacement="yes";

        if(str_batch.equals("Select")) {
            etbatch.setError("Select Your Batch");
            strbatch="no";
        }
        else if(!str_batch.equals("Select")) {
            etbatch.setError(null);
            strbatch="yes";
        }
        else
            strbatch="yes";

        if(str_ssc.equals("Select")) {
            etssc.setError("Select Your SSC");
            strssc="no";
        }
        else if(!str_ssc.equals("Select")) {
            etssc.setError(null);
            strssc="yes";
        }
        else
            strssc="yes";

        if(str_address.length()<1) {
            etaddress.setError("Address Required");
            straddress="no";
        }
        else
            straddress="yes";

        if(str_pin.length()!=6) {
            etpin.setError("Enter Valid Pin Code");
            strpin="no";
        }
        else
            strpin="yes";

        if(str_aadhar.length()!=12) {
            etaadhar.setError("Enter Valid Aadhar Number");
            straadhar="no";
        }
        else
            straadhar="yes";

        if(!isValidEmail(str_email)) {
            etemail.setError("Enter Correct Email");
            stremail="no";
        }
        else
            stremail="yes";

        if(str_cnumber.length()!=10) {
            etcnumber.setError("Enter Correct Mobile Number");
            strcnumber="no";
        }
        else
            strcnumber="yes";

        if(str_jrole.equals("Select")) {
            etjrole.setError("Select Your Job Role");
            strjrole="no";
        }
        else if(!str_jrole.equals("Select")) {
            etjrole.setError(null);
            strjrole="yes";
        }
        else
            strjrole="yes";

        if(str_edulevel.equals("Select")) {
            etedulevel.setError("Select Your Education Level");
            stredulevel="no";
        }
        else if(!str_edulevel.equals("Select")) {
            etedulevel.setError(null);
            stredulevel="yes";
        }
        else
            stredulevel="yes";

        if(strsid.equals("yes") && strfname.equals("yes") && strlname.equals("yes") && strpass.equals("yes") &&
                strrepass.equals("yes") && strgender.equals("yes") && strdob.equals("yes") &&
                strsdmsnumber.equals("yes") && strplacement.equals("yes") && strbatch.equals("yes") &&
                strssc.equals("yes") && straddress.equals("yes") && strpin.equals("yes") && straadhar.equals("yes") &&
                stremail.equals("yes") && strcnumber.equals("yes") && strjrole.equals("yes") && stredulevel.equals("yes")) {

            return true;
        }
        else
            return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
