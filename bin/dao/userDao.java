public interface UserDAO {
    //	public List<String> getListOfCuisines() throws Exception;
    //	public List<Patient> checkRegistrationAvailability(Patient culinary) throws Exception;
        public Patient addUserDetails(Patient patient)throws Exception;
        public Blood updateTheForm(Blood blood)throws Exception;
        public Doctor addDoctorDetails(Doctor doctor)throws Exception;
        public Doctor confirm(Integer dId,Doctor doctor)throws Exception;
        public Doctor delete(Integer dId)throws Exception;
        public Login getDetails(int id, String password) throws Exception;
        public Doctor getDoctorDetails(int id) throws Exception;
        public Patient getPatientDetails(int pid) throws Exception;
        public Appointment cancelAppointment(Integer reference)throws Exception;
        public String getPatientName(int pid) throws Exception;
        public List<Integer>  getDoctorList() throws Exception;
        public Appointment bookAppointment(Appointment appointment)throws Exception;
        public Map<Integer, String>  getDoctors(String department) throws Exception;
        //public List<Integer> fetchReferenceList(int pid) throws Exception;
        public List<Appointment> fetchReferenceList2(int dId) throws Exception;
        public Appointment fetchAppointmentDetails(int reference) throws Exception;
        public List<Patient> showAppointments(int id, String date1) throws Exception;
        public List<Patient> showAppointments(Appointment appointment) throws Exception;
        public List<Integer> fetchReferenceList1(int did) throws Exception;
        public Records addRecord(Records record) throws Exception;
        public Records fetchHealthRecord(int pid) throws Exception;
        public List<Appointment> fetchAppointmentList(int pId) throws Exception;
        
    } 
    
    
    
    //Editable link.... notepad.pw/251997 password-- 25/08/1997