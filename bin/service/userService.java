public interface UserService {
    //	public List<String> getListOfCuisines() throws Exception;
        public Patient register(Patient patient) throws Exception;
        public Blood updateTheForm(Blood blood) throws Exception;
        public Doctor registerDoctor(Doctor doctor) throws Exception;
        public Appointment cancelAppointment(Integer reference) throws Exception;
        public Doctor delete(Integer dId) throws Exception;
        public Doctor confirm(Integer dId,Doctor doctor) throws Exception;
        public Login getDetails(int id,String password) throws Exception;
        public Doctor fetchDoctorDetails(int id) throws Exception;
        public Patient fetchPatientDetails(int pid) throws Exception;
        public String fetchNameOfPatient(int pid) throws Exception;
        public List<Integer> fetchDoctorList() throws Exception;
        public Map<Integer, String> fetchDoctors(String department) throws Exception;
        public Appointment bookAppointment(Appointment appointment) throws Exception;
        public Appointment fetchAppointmentDetails(int reference) throws Exception;
        //public List<Integer> fetchReferenceList(int pid) throws Exception;
        public List<Appointment> fetchReferenceList2(int dId) throws Exception;
        public List<Patient> showAppointments(Appointment appointment)throws Exception;
        public List<Integer> fetchReferenceList1(int did) throws Exception;
        public Records addRecords(Records record) throws Exception;
        public Records fetchHealthRecord(int pid) throws Exception;
        public List<Appointment> fetchAppointmentList(int pId) throws Exception;
    }