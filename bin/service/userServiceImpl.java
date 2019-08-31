public class UserServiceImpl implements UserService {
	
	@Override
	public Patient register(Patient patient) throws Exception {
		try {
			
			UserDAO userDAO= Factory.createUserDAO();
			patient=userDAO.addUserDetails(patient);
		}
		catch (Exception exception) {
			exception.printStackTrace();
			if (!exception.getMessage().contains("Add")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return patient;
	}
	@Override
	public List<Integer> fetchDoctorList() throws Exception {
		// TODO Auto-generated method stub
		List<Integer> ids;
		try {
			UserDAO userDAO = Factory.createUserDAO();
			ids=userDAO.getDoctorList();
		} catch (Exception exception) {
			
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			throw exception;
		}
		return ids;
	}
	@Override
	public Doctor confirm(Integer dId,Doctor doctor) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			
			UserDAO userDAO= Factory.createUserDAO();
			doctor=userDAO.confirm(dId,doctor);
		}
		catch (Exception exception) {
			exception.printStackTrace();
			if (!exception.getMessage().contains("Add")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return doctor;	
	}
	@Override
	public Doctor delete(Integer dId) throws Exception {
		// TODO Auto-generated method stub
		Doctor doctor=null;
		try {
			
			UserDAO userDAO= Factory.createUserDAO();
			doctor=userDAO.delete(dId);
		}
		catch (Exception exception) {
			exception.printStackTrace();
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			
			throw exception;
		}
		return doctor;	
	}
	@Override
	public Doctor registerDoctor(Doctor doctor) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			UserDAO userDAO= Factory.createUserDAO();
			doctor=userDAO.addDoctorDetails(doctor);
		}
		catch (Exception exception) {
			exception.printStackTrace();
			if (!exception.getMessage().contains("Add")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return doctor;
	}
	@Override
	public Login getDetails(int id, String password) throws Exception {
		// TODO Auto-generated method stub
		Login login;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			 login= userDAO.getDetails(id,password);
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return login;
	}
	@Override
	public Doctor fetchDoctorDetails(int id) throws Exception {
		// TODO Auto-generated method stub
		Doctor doctor;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			doctor= userDAO.getDoctorDetails(id);
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return doctor;
	}
	@Override
	public Map<Integer, String> fetchDoctors(String department) throws Exception {
		// TODO Auto-generated method stub
		Map<Integer, String> doctors;
		try {
			UserDAO userDAO = Factory.createUserDAO();
			doctors=userDAO.getDoctors(department);
			
		} catch (Exception exception) {
			
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			throw exception;
		}
		return doctors;
	}
	@Override
	public Appointment bookAppointment(Appointment appointment)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			UserDAO userDAO= Factory.createUserDAO();
			appointment=userDAO.bookAppointment(appointment);
		}
		catch (Exception exception) {
			exception.printStackTrace();
			if (!exception.getMessage().contains("Add")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			else if (!exception.getMessage().contains("Doctor"))
			{
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return appointment;
	}
	@Override
	public String fetchNameOfPatient(int pid) throws Exception {
		// TODO Auto-generated method stub
		String pname;
		try {
			UserDAO userDAO = Factory.createUserDAO();
			pname=userDAO.getPatientName(pid);
		} catch (Exception exception) {
			
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			throw exception;
		}
		return pname;
	}
	@Override
	public Patient fetchPatientDetails(int pid) throws Exception {
		// TODO Auto-generated method stub
		Patient patient;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			 patient= userDAO.getPatientDetails(pid);
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return patient;
		
	}
	@Override
	public Appointment cancelAppointment(Integer reference) throws Exception {
		// TODO Auto-generated method stub
		Appointment appointment=null;
		try {
			
			UserDAO userDAO= Factory.createUserDAO();
			appointment=userDAO.cancelAppointment(reference);
		}
		catch (Exception exception) {
			exception.printStackTrace();
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			
			throw exception;
		}
		return appointment;	
	}

	@Override
	public List<Appointment> fetchReferenceList2(int dId) throws Exception {
		// TODO Auto-generated method stub
		List<Appointment> references;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			references= userDAO.fetchReferenceList2(dId);
		} catch (Exception exception) {
			//System.out.println("In Userservice exception");
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return references;
	}
	@Override
	public Appointment fetchAppointmentDetails(int reference) throws Exception {
		// TODO Auto-generated method stub
		Appointment appointment=null;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			appointment= userDAO.fetchAppointmentDetails(reference);
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return appointment;
	}
	
	@Override
	public List<Patient> showAppointments(Appointment appointment)
			throws Exception {
		
		// TODO Auto-generated method stub
		List<Patient> patient;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			patient= userDAO.showAppointments(appointment);
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return patient;
	}
	@Override
	public List<Integer> fetchReferenceList1(int did) throws Exception {
		// TODO Auto-generated method stub
		List<Integer> references;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			references= userDAO.fetchReferenceList1(did);
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return references;
	}
	@Override
	public Records addRecords(Records record) throws Exception {
		// TODO Auto-generated method stub
try {
			
			UserDAO userDAO= Factory.createUserDAO();
			record=userDAO.addRecord(record);
		}
		catch (Exception exception) {
			exception.printStackTrace();
			if (!exception.getMessage().contains("Add")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return record;
	}
	@Override
	public Records fetchHealthRecord(int pid) throws Exception {
		// TODO Auto-generated method stub
		Records records=null;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			records= userDAO.fetchHealthRecord(pid);
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return records;
	}
	@Override
	public List<Appointment> fetchAppointmentList(int pId) throws Exception {
		// TODO Auto-generated method stub
		List<Appointment> references;
		try {
		
			UserDAO userDAO = Factory.createUserDAO();
			references= userDAO.fetchAppointmentList(pId);
		} catch (Exception exception) {
			exception.printStackTrace();
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return references;
	}
	
	@Override
	public Blood updateTheForm(Blood blood) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			UserDAO userDAO= Factory.createUserDAO();
			blood=userDAO.updateTheForm(blood);
		}
		catch (Exception exception) {
			exception.printStackTrace();
			if (!exception.getMessage().contains("Add")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		return blood;
	}
}
