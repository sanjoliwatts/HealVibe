public class UserDAOImpl implements UserDAO {
    /*Inserting a new Customer details into database*/

    public Patient addUserDetails(Patient patient) throws Exception{
        SessionFactory sessionFactory=null;
        Session session=null;
        Session session1=null;
       try {
            sessionFactory=HibernateUtility.createSessionFactory();                      
            session=sessionFactory.openSession();                      
            session1=sessionFactory.openSession();
            PatientEntity pe=new PatientEntity();  
            pe.setName(patient.getName());
            pe.setContactNo(patient.getContactNo());
            pe.setEmailId(patient.getEmailId());
            pe.setPassword(patient.getPassword());
            pe.setAge(patient.getAge());
            pe.setAnswer(patient.getAnswer());
            pe.setQuestion(patient.getQuestion());
            
            session.getTransaction().begin();      
            session.persist(pe);
            patient.setUserId(pe.getUserId());
            session.getTransaction().commit();
            LoginEntity le=new LoginEntity();
            le.setPassword(patient.getPassword());
            le.setUserId(patient.getUserId());
            le.setUsername(patient.getName());
            
            session1.getTransaction().begin();
            session1.persist(le);
            session1.getTransaction().commit();
       }catch (HibernateException exception) {			
    	   	Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
            throw new Exception("AddDetails.ERROR1");
       }catch (Exception exception) {	
    	   Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
            throw exception;
       }finally {
            if(session.isOpen()|| session!=null)
                  session.close();      
            if(session1.isOpen()|| session1!=null)
      			session1.close();                                                       //Closing session instance
            }
       
       		return patient;
       } 
    
    
    @Override
	public Map<Integer, String> getDoctors(String department) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		Integer count=10;
		Map<Integer, String> doctors= new HashMap<Integer, String>();
		String hql = "from DoctorEntity d where d.department='"+department+"'";
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			List<DoctorEntity> dentity=query.list(); 
            for(DoctorEntity de:dentity){
                doctors.put(de.getdId() ,de.getName());   
            }        
            
		} catch (Exception e) {
			e.printStackTrace();
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null  ) {
				session.close();
			}
		}
		return doctors;
	}

    @Override
	public Appointment bookAppointment(Appointment appointment)
			throws Exception {
		// TODO Auto-generated method stub
		 SessionFactory sessionFactory=null;
	       Session session=null;
	       try {
	       sessionFactory=HibernateUtility.createSessionFactory();      
    	   session = sessionFactory.openSession();
    	   
	       Criteria cr = session.createCriteria(AppointmentEntity.class);
	       Criterion did = Restrictions.eq("dId", appointment.getdId());
	       Criterion date = Restrictions.eq("app_date",appointment.getApp_date());
	       LogicalExpression andExp = Restrictions.and(did, date);
	       cr.add( andExp );
	       Criteria cr1 = session.createCriteria(AppointmentEntity.class);
	       Criterion pid = Restrictions.eq("pid", appointment.getPid());
	       Criterion date1 = Restrictions.eq("app_date",appointment.getApp_date());
	       LogicalExpression andExp1 = Restrictions.and(pid, date1);
	       cr1.add( andExp1 );
	   	List<AppointmentEntity> aentity1=cr1.list();
	   	if(aentity1.size()!=0)
	   	{
	   		throw new Exception("UserExists");
	   	}
				List<AppointmentEntity> aentity=cr.list();
				if(aentity.size()<10)
				{
		            AppointmentEntity ae=new AppointmentEntity();  
		            ae.setApp_date(appointment.getApp_date());
		            ae.setDepartment(appointment.getDepartment());
		            ae.setdName(appointment.getdName());
		            ae.setdId(appointment.getdId());
		            ae.setPid(appointment.getPid());
			            if(ae!=null) 
			            {
				            session.getTransaction().begin();      
				            session.persist(ae);
				            appointment.setReference(ae.getReference());
				            session.getTransaction().commit();
			            }
				} 
				else
				{
					throw new Exception("DoctorNotAvailaible");
				}
	    
	       }catch (HibernateException exception) {			
	    	   exception.printStackTrace();
	    	   	Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
	            throw exception;
	       }catch (Exception exception) {	
	    	   exception.printStackTrace();
	    	   Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
	            throw exception;
	       }finally {
	            if(session.isOpen()|| session!=null)
	                  session.close(); 
	            }
	       return appointment; 
	      }
	@Override
	public Login getDetails(int userId, String password) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		Login login=null;
		String hql = "from LoginEntity u where u.userId="+userId +" and u.password='"+password+"' ";
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);

			LoginEntity lentity= (LoginEntity) query.uniqueResult(); 
			if(lentity!=null){
				login=new Login();
				
				login.setUsername(lentity.getUsername());
				login.setPassword(lentity.getPassword());
				
				login.setUserId(lentity.getUserId());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session.isOpen() || session != null) {
				session.close();
			}
		}
		return login;
	}

	@Override
	public List<Integer> getDoctorList() throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		List<Integer> ids=new ArrayList<Integer>();
		String hql = "from DoctorEntity d where d.status='P'";
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			List<DoctorEntity> dentity=query.list();  
            for(DoctorEntity de:dentity){
                ids.add(de.getdId());   
            }        
		} catch (Exception e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return ids;
	}

	@Override
	public Doctor confirm(Integer dId,Doctor doctor) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory=null;
        Session session=null;
        Session session1=null;
        int rows=0;
       try {
            sessionFactory=HibernateUtility.createSessionFactory();                      
            session=sessionFactory.openSession();
            session1=sessionFactory.openSession();
            String hql = "from DoctorEntity u where u.dId="+dId;
            String hql1 = "Update DoctorEntity d set d.status='A' where d.dId="+dId;
            Query query = session.createQuery(hql);
            Query query1 = session.createQuery(hql1); 
            DoctorEntity dentity = (DoctorEntity) query.uniqueResult();
            session1.getTransaction().begin();
            rows=query1.executeUpdate();
            session1.getTransaction().commit();
            if(dentity!=null){
            	LoginEntity le=new LoginEntity();
            	le.setUserId(dentity.getdId());
            	le.setPassword(dentity.getPassword());
            	le.setUsername(dentity.getName());		
            	session.getTransaction().begin();      
            	session.persist(le);
            	session.getTransaction().commit();     
            }
       }catch (HibernateException exception) {			
    	   	Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			exception.printStackTrace();
            throw new Exception("Confirm.status");
       }catch (Exception exception) {	
    	   Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
            throw exception;
       }finally {
            if(session.isOpen()|| session!=null)
                  session.close();                                                       //Closing session instance
            if(session1.isOpen()|| session1!=null)
                session1.close();               
            }
       return doctor;
       }

	@Override
	public Doctor delete(Integer dId) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory= null;
	       Session session=null;
	       Doctor doctor=null;
	       try {
	            sessionFactory=HibernateUtility.createSessionFactory();
	            session=sessionFactory.openSession(); 
	            String hql = "delete from DoctorEntity ce where ce.dId="+dId; 
	                Query query = session.createQuery(hql);                                 
	                session.beginTransaction();
	               query.executeUpdate();                               
	                session.getTransaction().commit();
	                doctor=new Doctor();
		   }catch (HibernateException exception) {	
			   exception.printStackTrace();
	            throw new Exception("DAO.TECHNICAL_ERROR");
	       }catch (Exception exception) {			
	            throw exception;
	       }finally {
	            if(session.isOpen()|| session!=null)
	                  session.close();
	            }
	       return doctor;
	}

	@Override
	public Doctor addDoctorDetails(Doctor doctor) throws Exception {
		// TODO Auto-generated method stub
		 SessionFactory sessionFactory=null;
	        Session session=null;
	       try {
	            sessionFactory=HibernateUtility.createSessionFactory();                      
	            session=sessionFactory.openSession();                                        
	            DoctorEntity de=new DoctorEntity();  
	            de.setName(doctor.getName());
	            de.setContactNo(doctor.getContactNo());
	            de.setEmailId(doctor.getEmailId());
	            de.setPassword(doctor.getPassword());
	            de.setAge(doctor.getAge());
	            de.setAnswer(doctor.getAnswer());
	            de.setQuestion(doctor.getQuestion());
	            de.setDepartment(doctor.getDepartment());
	            de.setExperience(doctor.getExperience());
	            de.setStatus("P");
	            de.setPatientCount(0);
	            session.getTransaction().begin();      
	            session.persist(de);
	            doctor.setdId(de.getdId());
	            session.getTransaction().commit();      
	       }catch (HibernateException exception) {			
	    	   exception.printStackTrace();
	    	   	Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
	            throw new Exception("AddDetails.ERROR1");
	       }catch (Exception exception) {	
	    	   exception.printStackTrace();
	    	   Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
	            throw exception;
	       }finally {
	            if(session.isOpen()|| session!=null)
	                  session.close();                                                       //Closing session instance
	            }
	       return doctor;
	      }

	@Override
	public Doctor getDoctorDetails(int id) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		Doctor doctor=null;
		String hql = "from DoctorEntity d where d.dId="+id;
		try {
			session = sessionFactory.openSession();
		
			Query query = session.createQuery(hql);
			DoctorEntity dentity= (DoctorEntity) query.uniqueResult(); 
			if(dentity!=null){
				doctor=new Doctor();
				doctor.setName(dentity.getName());
				doctor.setDepartment(dentity.getDepartment());
				doctor.setExperience(dentity.getExperience());
				
			}
		} catch (Exception e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return doctor;
	}


	@Override
	public String getPatientName(int pid) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		String pname;
		String hql = "from LoginEntity l where l.userId="+pid;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			LoginEntity lentity=(LoginEntity) query.uniqueResult(); 
            pname=lentity.getUsername();     
		} catch (Exception e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return pname;
	}


	@Override
	public Patient getPatientDetails(int pid) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		Patient patient=null;
		String hql = "from PatientEntity p where p.userId="+pid ;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);

			PatientEntity pentity= (PatientEntity) query.uniqueResult(); 
			if(pentity!=null){
				patient=new Patient();
				
				patient.setName(pentity.getName());
				patient.setEmailId(pentity.getEmailId());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session.isOpen() || session != null) {
				session.close();
			}
		}
		return patient;
	}

	
	public List<Appointment> fetchReferenceList2(int dId) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		Appointment appointment =null;
		List<Appointment> references=new ArrayList<Appointment>();
		String hql = "from AppointmentEntity ae where ae.dId="+dId;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			List<AppointmentEntity> aentity=query.list();  
            for(AppointmentEntity ae:aentity){
            	appointment=new Appointment();
				appointment.setApp_date(ae.getApp_date());
				appointment.setDepartment(ae.getDepartment());
				appointment.setdId(ae.getdId());
				appointment.setdName(ae.getdName());
				appointment.setPid(ae.getPid());
				appointment.setReference(ae.getReference());
             //   references.add(ae.getReference());   
            	references.add(appointment);
            }        
		} catch (Exception e) {
			e.printStackTrace();
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return references;
	}
	@Override
	public Appointment cancelAppointment(Integer reference) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory= null;
	       Session session=null;
	       Appointment appointment=null;
	       try {
	            sessionFactory=HibernateUtility.createSessionFactory();
	            session=sessionFactory.openSession(); 
	            appointment=new Appointment();
	            String hql = "delete from AppointmentEntity ae where ae.reference="+reference; 
	                Query query = session.createQuery(hql);                                // Creating the query object 
	                session.beginTransaction();
	               query.executeUpdate();                               // Executing the query
	                session.getTransaction().commit();
	          
		   }catch (HibernateException exception) {	
			   exception.printStackTrace();
	            throw new Exception("DAO.TECHNICAL_ERROR");
	       }catch (Exception exception) {
	    	  exception.printStackTrace();
	            throw exception;
	       }finally {
	            if(session.isOpen()|| session!=null)
	                  session.close();
	            }
	       return appointment;
	}
	@Override
	public Appointment fetchAppointmentDetails(int reference) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		Appointment appointment=null;
		String hql = "from AppointmentEntity ae where ae.reference="+reference;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			AppointmentEntity aentity= (AppointmentEntity) query.uniqueResult(); 
			if(aentity!=null){
				appointment=new Appointment();
				appointment.setApp_date(aentity.getApp_date());
				appointment.setDepartment(aentity.getDepartment());
				appointment.setdId(aentity.getdId());
				appointment.setdName(aentity.getdName());
				appointment.setPid(aentity.getPid());
				appointment.setReference(aentity.getReference());
			}
		} catch (Exception e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return appointment;
	}


	@Override
	public List<Patient> showAppointments(int id, String date1) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		List<Patient> patient=new ArrayList();
		String hql = "select pid from AppointmentEntity ae where ae.did="+id+" and app_date='"+date1+"'";
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			List<Integer> aentity= query.list();
			Patient p=null;
			for(Integer i:aentity){
				hql = "from PatientEntity pe where pe.userId="+i;
				query = session.createQuery(hql);
				PatientEntity pe=(PatientEntity) query.uniqueResult();
				p=new Patient();
				p.setContactNo(pe.getContactNo());
				p.setEmailId(pe.getEmailId());
				p.setUserId(pe.getUserId());
				patient.add(p);
			}
			if(patient==null)
				throw new Exception("");
		} catch (Exception e) {
			e.printStackTrace();
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session.isOpen() || session != null) {
				session.close();
			}
		}
		return patient;
	}


	@Override
	public List<Patient> showAppointments(Appointment appointment)
			throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		List<Patient> patient=new ArrayList();
	
		try {
			session = sessionFactory.openSession();
			List<AppointmentEntity> aentity=null;
			
			Criteria cr = session.createCriteria(AppointmentEntity.class);
		    Criterion did = Restrictions.eq("dId", appointment.getdId());
		    Criterion date = Restrictions.eq("app_date",appointment.getApp_date());
		    System.out.println(date);
		    LogicalExpression andExp = Restrictions.and(did, date);
		    cr.add( andExp );
			
			aentity=cr.list();
			
			Patient p=null;
			for(AppointmentEntity i:aentity){
				System.out.println(i);
				String hql = "from PatientEntity pe where pe.userId="+i.getPid();
				Query query = session.createQuery(hql);
				PatientEntity pe=(PatientEntity) query.uniqueResult();
				p=new Patient();
				p.setContactNo(pe.getContactNo());
				p.setEmailId(pe.getEmailId());
				p.setUserId(pe.getUserId());
				patient.add(p);
			}
			if(patient==null)
				throw new Exception("patient Not Exist");
		} catch (Exception e) {
			e.printStackTrace();
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session.isOpen() || session != null) {
				session.close();
			}
		}
		return patient;
	}


	@Override
	public List<Integer> fetchReferenceList1(int did) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		List<Integer> references=new ArrayList<Integer>();
		String hql = "from AppointmentEntity ae where ae.dId="+did;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			List<AppointmentEntity> aentity=query.list();  
            for(AppointmentEntity ae:aentity){
            	System.out.println(1);
                references.add(ae.getReference());   
            }        
            
		} catch (Exception e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return references;
	}


	@Override
	public Records addRecord(Records record) throws Exception {
		// TODO Auto-generated method stub
		 SessionFactory sessionFactory=null;
	        Session session=null;
	       try {
	    	   
	            sessionFactory=HibernateUtility.createSessionFactory();    
	            session=sessionFactory.openSession();  
	            RecordsEntity ce=(RecordsEntity) session.get(RecordsEntity.class, record.getpId());
	            
	            if(ce==null)
	            {
	            RecordsEntity re=new RecordsEntity();  
	            re.setAllergies(record.getAllergies());
	            re.setDoctorName(record.getDoctorName());
	            re.setHeight(record.getHeight());
	            re.setIssueList(record.getIssueList());
	            re.setMedications(record.getMedications());
	            re.setpId(record.getpId());
	            re.setWeight(record.getWeight());
	            
	            session.getTransaction().begin();     
	            session.persist(re);
	            session.getTransaction().commit();
	            }
	            else
	            {
	            	 session.getTransaction().begin();
	                  ce.setAllergies(record.getAllergies());
	                  ce.setDoctorName(record.getDoctorName());
	                  ce.setHeight(record.getHeight());
	                  ce.setIssueList(record.getIssueList());
	                  ce.setMedications(record.getMedications());
	                  ce.setWeight(record.getWeight());
	                 session.getTransaction().commit();
	            }
	           	       }
	       catch (HibernateException exception) {		
	    	   exception.printStackTrace();
	    	   	Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
	            throw new Exception("AddDetails.ERROR1");
	       }catch (Exception exception) {	
	    	   exception.printStackTrace();
	    	   Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
	            throw exception;
	       }finally {
	            if(session.isOpen()|| session!=null)
	                  session.close();      
	                                              
	            }
	       
	       	return record;
	}


	@Override
	public Records fetchHealthRecord(int pid) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		Records records =null;
		//List<Appointment> references=new ArrayList<Appointment>();
		String hql = "from RecordsEntity re where re.pId="+pid;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			RecordsEntity rentity=(RecordsEntity) query.uniqueResult();
           
			if(rentity!=null){
				records=new Records();
			    records.setAllergies(rentity.getAllergies());
			    records.setDoctorName(rentity.getDoctorName());
			    records.setHeight(rentity.getHeight());
			    records.setIssueList(rentity.getIssueList());
			    records.setMedications(rentity.getMedications());
			    records.setpId(rentity.getpId());
			    records.setWeight(rentity.getWeight());
			}
             
     //     System.out.println("references  "+references);  
		} catch (Exception e) {
			e.printStackTrace();
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return records;
	}


	@Override
	public List<Appointment> fetchAppointmentList(int pId) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtility.createSessionFactory();
		Session session = null;
		Appointment appointment =null;
		List<Appointment> references=new ArrayList<Appointment>();
		String hql = "from AppointmentEntity ae where ae.pid="+pId;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			List<AppointmentEntity> aentity=query.list();  
            for(AppointmentEntity ae:aentity){
            	appointment=new Appointment();
				appointment.setApp_date(ae.getApp_date());
				appointment.setDepartment(ae.getDepartment());
				appointment.setdId(ae.getdId());
				appointment.setdName(ae.getdName());
				appointment.setPid(ae.getPid());
				appointment.setReference(ae.getReference());
             //   references.add(ae.getReference());   
            	references.add(appointment);
            }        
		} catch (Exception e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.getMessage(), e);
			throw new Exception("GettingDetails.ERROR1");
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return references;
	} 

	@Override
	public Blood updateTheForm(Blood blood) throws Exception {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory=null;
        Session session=null;
       try {
            sessionFactory=HibernateUtility.createSessionFactory();                      
            session=sessionFactory.openSession();                      
            
            BloodEntity be=new BloodEntity();  
            be.setBlood_group(blood.getBlood_group());
            be.setLast_date_of_donation(blood.getLast_date_of_donation());
            be.setPid(blood.getPid());
            session.getTransaction().begin();      
            session.persist(be);
            //persist the entity in db
            session.getTransaction().commit();
            
           // System.out.println("working-dao");//commit the transaction
       }catch (HibernateException exception) {			
    	   exception.printStackTrace();
    	   
    	   	Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
            throw new Exception("AddDetails.ERROR1");
       }catch (Exception exception) {	
    	  // exception.printStackTrace();
    	 //  System.out.println("Exception-dao");
    	   Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
            throw exception;
       }finally {
            if(session.isOpen()|| session!=null)
                  session.close();      
                                                             //Closing session instance
            //Closing session instance
            }
       
       		return blood;
       } 
	
	} 