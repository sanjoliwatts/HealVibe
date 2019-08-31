@Path("Hospital")
public class UserAPI {

	@Path("signup")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userAdd(String dataRecieved) throws Exception {
		Response response = null;
		String string = dataRecieved;
		try {
			Patient patient = JSONParser.fromJson(string, Patient.class);
			UserService us = Factory.createUserService();
			patient = us.register(patient);
			String successMessage = this.getBookingSuccessMessage(patient);
			patient.setMessage(successMessage);
			String returnString = JSONParser.toJson(patient);
			response = Response.status(Status.OK).entity(returnString).build();
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Patient patient = new Patient();
			patient.setMessage(errorMessage);

			String returnValue = JSONParser.toJson(patient);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnValue).build();
		}
		return response;

	}
@Path("register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doctorAdd(String dataRecieved) throws Exception {
		Response response = null;
		String string = dataRecieved;
		try {
			Doctor doctor = JSONParser.fromJson(string, Doctor.class);
			UserService us = Factory.createUserService();
			doctor = us.registerDoctor(doctor);
			String successMessage = this.getBookingSuccessMessage1(doctor);
			doctor.setMessage(successMessage);
			String returnString = JSONParser.toJson(doctor);
			response = Response.status(Status.OK).entity(returnString).build();
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Doctor doctor = new Doctor();
			doctor.setMessage(errorMessage);

			String returnValue = JSONParser.toJson(doctor);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnValue).build();
		}
		return response;

	}

	private String getBookingSuccessMessage1(Doctor doctor) {
		String message = null;

		message = AppConfig.PROPERTIES
				.getProperty("RegistrationBean.REGISTRATION_SUCCESS1");
		message += doctor.getName();
		message += AppConfig.PROPERTIES
				.getProperty("RegistrationBean.REGISTRATION_SUCCESS2");
		message += AppConfig.PROPERTIES
				.getProperty("RegistrationBean.REGISTRATION_SUCCESS3");
		message += doctor.getdId();

		return message;
	}

	private String getBookingSuccessMessage(Patient patient) {
		String message = null;

		message = AppConfig.PROPERTIES
				.getProperty("RegistrationBean.REGISTRATION_SUCCESS1");
		message += patient.getName();
		message += AppConfig.PROPERTIES
				.getProperty("RegistrationBean.REGISTRATION_SUCCESS2");
		message += AppConfig.PROPERTIES
				.getProperty("RegistrationBean.REGISTRATION_SUCCESS3");
		message += patient.getUserId();

		return message;
	}

	private String getSuccessMessage(Login login) {
		String message = null;

		/*
		 * message = AppConfig.PROPERTIES
		 * .getProperty("GettingDetails.SUCCESS1"); message +=
		 * login.getUsername(); message += AppConfig.PROPERTIES
		 * .getProperty("GettingDetails.SUCCESS2"); message +=
		 * login.getUserId()+" ";
		 */
		if (login.getUserId() == 529983)
			message = "Admin";
		else if (login.getUserId() % 2 == 0)
			message = "Doctor";
		else
			message = "Patient";

		//System.out.println(message);
		return message;
	}

	private String getCancelSuccessMessage(Appointment appointment) {
		String message = null;

		message += AppConfig.PROPERTIES.getProperty("GettingDetails.SUCCESS3");

		return message;
	}

	@Path("fetchdetails/{id}/{password}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchdetails(@PathParam("id") int id,
			@PathParam("password") String password) throws Exception {
		Response response = null;
		try {
			UserService userService = Factory.createUserService();
			Login login = userService.getDetails(id, password);
			if (login != null) {
				String successMessage = this.getSuccessMessage(login);
				login.setMessage(successMessage);
				String returnString = JSONParser.toJson(login);
				response = Response.status(Status.OK).entity(returnString)
						.build();
			} else {
				throw new Exception("GettingDetails.ERROR1");
			}

		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Patient patient = new Patient();
			patient.setMessage(errorMessage);

			String returnString = JSONParser.toJson(patient);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}

	@Path("fetchDoctorList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchDoctorList() throws Exception {
		Response response = null;
		try {
			UserService userService = Factory.createUserService();
			List<Integer> ids = userService.fetchDoctorList();
			//System.out.println(ids);
			if (ids != null) {
				String returnString = JSONParser.toJson(ids);
				response = Response.status(Status.OK).entity(returnString)
						.build();
			} else {
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Doctor doctor = new Doctor();
			doctor.setMessage(errorMessage);

			String returnString = JSONParser.toJson(doctor);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}

	@Path("fetchDoctordetails/{did}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchDoctorDetails(@PathParam("did") Integer dId)
			throws Exception {
		Response response = null;
		try {
			UserService userService = Factory.createUserService();
			Doctor doctor = userService.fetchDoctorDetails(dId);

			if (doctor != null) {
				String returnString = JSONParser.toJson(doctor);
				response = Response.status(Status.OK).entity(returnString)
						.build();
			} else {
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Doctor doctor = new Doctor();
			doctor.setMessage(errorMessage);

			String returnString = JSONParser.toJson(doctor);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}

	@Path("confirm/{did}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response confirm(@PathParam("did") Integer dId, String dataRecieved)
			throws Exception {
		Response response = null;
		String string = dataRecieved;
		Doctor doctor = null;
		try {
			UserService us = Factory.createUserService();
			doctor = JSONParser.fromJson(string, Doctor.class);
			doctor = us.confirm(dId, doctor);
			String successMessage = this.getAddSuccessMessage(doctor);
			doctor.setMessage(successMessage);
			String returnString = JSONParser.toJson(doctor);
			response = Response.status(Status.OK).entity(returnString).build();
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			doctor = new Doctor();
			doctor.setMessage(errorMessage);

			String returnValue = JSONParser.toJson(doctor);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnValue).build();
		}
		return response;

	}

	private String getAddSuccessMessage(Doctor doctor) {
		// TODO Auto-generated method stub
		String message = null;

		message = "Successfully Approved";
		return message;
	}

	private String getAddRecordMessage(Records record)
	{
		String message = null;

		message = AppConfig.PROPERTIES
				.getProperty("RecordAdded");
		return message;
	}
	private String getBookingAppointmentSuccessMessage(Appointment appointment) {
		String message = null;

		message = AppConfig.PROPERTIES
				.getProperty("RegistrationBean.REGISTRATION_SUCCESS4");
		message += " ";
		message += appointment.getReference();

		return message;
	}

	private String getAddSuccessMessage1(Doctor doctor) {
		// TODO Auto-generated method stub
		String message = null;

		message = "Successfully Deleted";
		return message;
	}

	@Path("delete/{did}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("did") Integer data) throws Exception {
		Response response = null;
		Integer dId = data;
		Doctor doctor = null;
		try {
			UserService us = Factory.createUserService();

			doctor = us.delete(dId);
			String successMessage = this.getAddSuccessMessage1(doctor);
			doctor.setMessage(successMessage);
			String returnString = JSONParser.toJson(doctor);
			response = Response.status(Status.OK).entity(returnString).build();
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());
			doctor = new Doctor();
			doctor.setMessage(errorMessage);
			String returnValue = JSONParser.toJson(doctor);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnValue).build();
		}
		return response;

	}

	@Path("fetchDoctors/{department}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchDoctors(@PathParam("department") String department)
			throws Exception {
		Response response = null;
		Map<Integer, String> hMap = new HashMap<Integer, String>();
		try {
		//	System.out.println(department);
			//System.out.println("in service");
			UserService userService = Factory.createUserService();
			// List<String> doctors = userService.fetchDoctors(department);
			hMap = userService.fetchDoctors(department);
			//System.out.println(hMap.keySet());
			if (hMap != null) {
				String returnString = JSONParser.toJson(hMap);
				response = Response.status(Status.OK).entity(returnString)
						.build();
			} else {
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Doctor doctor = new Doctor();
			doctor.setMessage(errorMessage);

			String returnString = JSONParser.toJson(doctor);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}

	@Path("bookAppointment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response bookAppointment(String dataRecieved) throws Exception {
		Response response = null;
		String string = dataRecieved;
		try {
			Appointment appointment = JSONParser.fromJson(string,
					Appointment.class);
			UserService us = Factory.createUserService();
			//System.out.println("In Api "+appointment.getApp_date());
			appointment = us.bookAppointment(appointment);
			//System.out.println("In Api 2"+appointment.getApp_date());
			String successMessage = this
					.getBookingAppointmentSuccessMessage(appointment);
			appointment.setMessage(successMessage);
			String returnString = JSONParser.toJson(appointment);
			response = Response.status(Status.OK).entity(returnString).build();
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Appointment appointment = new Appointment();
			appointment.setMessage(errorMessage);

			String returnValue = JSONParser.toJson(appointment);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnValue).build();
		}
		return response;

	}

	@Path("fetchNameOfPatient/{pid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchNameOfPatient(@PathParam("pid") Integer pId)
			throws Exception {
		Response response = null;
		//System.out.println("Inside API");
		try {
			UserService userService = Factory.createUserService();
			String pname = userService.fetchNameOfPatient(pId);
			//System.out.println("In Api " + pname);
			if (pname != null) {
				Login log = new Login();
				log.setUsername(pname);
				String returnString = JSONParser.toJson(log);
				//System.out.println("returnString " + returnString);
				response = Response.status(Status.OK).entity(returnString)
						.build();
			} else {
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			//System.out.println("Inside userapi exception");
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Patient patient = new Patient();
			patient.setMessage(errorMessage);

			String returnString = JSONParser.toJson(patient);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		//System.out.println(response);
		return response;
	}

	@Path("fetchDetailsOfPatient/{pid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchDetailsOfPatient(@PathParam("pid") Integer pId)
			throws Exception {
		Response response = null;
		try {
			UserService userService = Factory.createUserService();
			Patient patient = userService.fetchPatientDetails(pId);

			if (patient != null) {
				String returnString = JSONParser.toJson(patient);
				response = Response.status(Status.OK).entity(returnString)
						.build();
			} else {
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Doctor doctor = new Doctor();
			doctor.setMessage(errorMessage);

			String returnString = JSONParser.toJson(doctor);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}

	@Path("cancelAppointment/{reference}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelAppointment(@PathParam("reference") Integer data)
			throws Exception{
		Response response = null;
        Integer reference = data;
        Appointment appointment=null;
        try
        {
        	UserService us=Factory.createUserService();  
        	//System.out.println("cancel api");
        	appointment=us.cancelAppointment(reference);
        	String successMessage = this.getCancelSuccessMessage(appointment);
        	appointment.setMessage(successMessage);
        	String returnString = JSONParser.toJson(appointment);
            response = Response.status(Status.OK).entity(returnString).build();
        }
        catch (Exception e) {
        	e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e.getMessage());
			
			appointment = new Appointment();
			appointment.setMessage(errorMessage);
			
			String returnValue = JSONParser.toJson(appointment);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnValue).build();
		}
        return response;
		
	}
	@Path("fetchReferenceList/{did}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchReferenceList(@PathParam("did") Integer did) throws Exception {
		Response response = null;
		try {
			//System.out.println(did);
			UserService userService = Factory
					.createUserService();
		//	List<Integer> references= userService.fetchReferenceList(pid);
			List<Appointment> appointmentList =  userService.fetchReferenceList2(did);
			if(appointmentList!=null){
				String returnString = JSONParser.toJson(appointmentList);
				response = Response.status(Status.OK).entity(returnString).build();
			}
			else{
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e.getMessage());
			
			Appointment appointment = new Appointment();
			appointment.setMessage(errorMessage);
			
			String returnString = JSONParser.toJson(appointment);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	@Path("fetchAppointmentDetails/{reference}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAppointmentDetails(@PathParam("reference") Integer reference) throws Exception {
		Response response = null;
		try {
			UserService userService = Factory
					.createUserService();
			Appointment appointment= userService.fetchAppointmentDetails(reference);
			
			if(appointment!=null){
				String returnString = JSONParser.toJson(appointment);
				response = Response.status(Status.OK).entity(returnString).build();
			}
			else{
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e.getMessage());
			
			Appointment appointment = new Appointment();
			appointment.setMessage(errorMessage);
			
			String returnString = JSONParser.toJson(appointment);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	
	@Path("showAppointments")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//public Response showAppointmets(@PathParam("id") int id,@PathParam("date1") Calendar date1) throws Exception {
	public Response showAppointmets(String data) throws Exception {

		Response response = null;
		String string = data;
		try {
		//	System.out.println("In userapi");
			Appointment appointment = JSONParser.fromJson(string,
					Appointment.class);
			UserService userService = Factory
					.createUserService();
			List<Patient> patient  = userService.showAppointments(appointment);
		//	List<Patient> patient=null ;
			//System.out.println("In API");
			if(patient!=null){
				
				String returnString = JSONParser.toJson(patient);
				response = Response.status(Status.OK).entity(returnString).build();
				//System.out.println(response);
			}
			else{
				throw new Exception("GettingDetails.ERROR1");
			}
		
			
		} catch (Exception e) {
			//System.out.println("Inside userapi exception");
			String errorMessage = AppConfig.PROPERTIES.getProperty(e.getMessage());
			
			Patient patient = new Patient();
			patient.setMessage(errorMessage);
			
			String returnString = JSONParser.toJson(patient);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	
	@Path("fetchReferenceList1/{did}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchReferenceList1(@PathParam("did") Integer did) throws Exception {
		Response response = null;
		try {
			//System.out.println(did);
			UserService userService = Factory
					.createUserService();
			List<Integer> references= userService.fetchReferenceList1(did);
			
			if(references!=null){
				String returnString = JSONParser.toJson(references);
				response = Response.status(Status.OK).entity(returnString).build();
			}
			else{
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e.getMessage());
			
			Appointment appointment = new Appointment();
			appointment.setMessage(errorMessage);
			
			String returnString = JSONParser.toJson(appointment);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	
	@Path("addRecords")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRecords(String dataRecieved) throws Exception {
		Response response = null;
		String string = dataRecieved;
		try {
			Records record = JSONParser.fromJson(string, Records.class);
			UserService us = Factory.createUserService();
			record = us.addRecords(record);
			String successMessage = this.getAddRecordMessage(record);
			record.setMessage(successMessage);
			String returnString = JSONParser.toJson(record);
			response = Response.status(Status.OK).entity(returnString).build();
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Patient patient = new Patient();
			patient.setMessage(errorMessage);

			String returnValue = JSONParser.toJson(patient);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnValue).build();
		}
		return response;

	}
	@Path("fetchHealthRecord/{pid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchHealthRecord(@PathParam("pid") Integer pid) throws Exception {
		Response response = null;
		try {
			UserService userService = Factory
					.createUserService();
			Records records = userService.fetchHealthRecord(pid);
			
			if(records!=null){
				String returnString = JSONParser.toJson(records);
				response = Response.status(Status.OK).entity(returnString).build();
			}
			else{
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			String errorMessage = AppConfig.PROPERTIES.getProperty(e.getMessage());
			
			Appointment appointment = new Appointment();
			appointment.setMessage(errorMessage);
			
			String returnString = JSONParser.toJson(appointment);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	@Path("fetchAppointmentList/{pid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAppointmentList(@PathParam("pid") Integer pid) throws Exception {
		Response response = null;
		try {
			//System.out.println(pid);
			UserService userService = Factory
					.createUserService();
		//	List<Integer> references= userService.fetchReferenceList(pid);
			List<Appointment> appointmentList =  userService.fetchAppointmentList(pid);
			if(appointmentList!=null){
				String returnString = JSONParser.toJson(appointmentList);
				response = Response.status(Status.OK).entity(returnString).build();
			}
			else{
				throw new Exception("GettingDetails.ERROR1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e.getMessage());
			
			Appointment appointment = new Appointment();
			appointment.setMessage(errorMessage);
			
			String returnString = JSONParser.toJson(appointment);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	@Path("updateTheForm")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTheForm(String data) throws Exception {
		Response response = null;
		String string = data;
		try {
			Blood blood = JSONParser.fromJson(string, Blood.class);
			UserService us = Factory.createUserService();
			blood = us.updateTheForm(blood);
			
			blood.setMessage("Successfully Added");
			String returnString = JSONParser.toJson(blood);
			response = Response.status(Status.OK).entity(returnString).build();
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			Blood blood = new Blood();
			blood.setMessage(errorMessage);

			String returnValue = JSONParser.toJson(blood);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnValue).build();
		}
		return response;

	}
}
