package com.gmhis_backk.repository;


import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.admission.Admission;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {


	@Query(value = "SELECT * FROM admission a WHERE a.id IN ( SELECT DISTINCT e.admission_id FROM examination e) AND  a.facility_id =:facilityId", nativeQuery = true)
	public Page<Admission> findAdmissionWithExamination(@Param("facilityId") String facilityId,Pageable pageable);
	
	
	@Query(value = "select * from  admission a order by a.created_at desc LIMIT 0,1", nativeQuery = true)
	public Admission findLastAdmission();

	@Modifying
	@Transactional
	@Query(value = "insert into admission_has_act ( admission_id, act_id,pratician_id, act_cost, bill_id, user_id ) values (:admission_id, :act_id, :pratician_id, :act_cost, :bill_id, :user_id)", nativeQuery = true)
	public void addActToAdmission(@Param("admission_id") Long admissionId, @Param("act_id") Long actId,
			@Param("pratician_id") Long particianId,  @Param("act_cost") int actCost,  @Param("bill_id") Long billId, @Param("user_id") Long userId);

	@Modifying
	@Transactional
	@Query(value = "delete from admission_has_act where admission_id = :admission_id and act_id = :act_id", nativeQuery = true)
	public void removeAdmissionAct(@Param("admission_id") Long admissionId, @Param("act_id") Long actId);

	@Modifying
	@Transactional
	@Query(value = "Update Admission a set admissionStatus='B' where a.id = :id")
	public void setAdmissionStatusToBilled(@Param("id") Long id);

	// admissions
	/****************************************************************************/
	@Query(value = "SELECT a FROM Admission a WHERE a.patient.firstName like %:firstName% and  (a.patient.lastName like %:lastName% ) and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByPatientName(@Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.admissionNumber like %:admissionNumber% and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByAdmissionNumber(@Param("admissionNumber") String admissionNumber,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.patient.patientExternalId like %:patientExternalId% and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByPatientExternalId(@Param("patientExternalId") String patientExternalId,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(
			value = "SELECT a FROM Admission a " +
			"WHERE a.patient.cellPhone1 like %:cellPhone%" +
			" and a.admissionStatus= :admissionStatus" +
			" AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByCellPhone(@Param("cellPhone") String cellPhone,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.patient.cnamNumber like %:cnamNumber% and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByCnamNumber(@Param("cnamNumber") String cnamNumber,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.patient.idCardNumber like %:idCardNumber% and a.admissionStatus= :admissionStatus  AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByIdCardNumber(@Param("idCardNumber") String idCardNumber,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	public Page<Admission> findAdmissionsByPractician(@Param("practician") Long practician,
			@Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.act.id = :act  and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByAct(@Param("act") Long act, @Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId,
			Pageable pageable);
	@Query(value = "SELECT a FROM Admission a WHERE a.type = :type and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findByType(@Param("type") String type,
												   @Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.service.id = :service and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByService(@Param("service") Long service,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.createdAt between :fromDate and :toDate and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionByDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
			@Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissions(@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);
	
	@Query(value = "SELECT a FROM Admission a WHERE a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByFacility(@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);
	
	@Query(value = "SELECT a FROM Admission a WHERE a.createdAt BETWEEN :start AND :end AND a.facilityId =:facilityId")
	Page<Admission> findByDate(Date start, Date end,@Param("facilityId") String facilityId, Pageable pageable);

	/**********************************  WAITING ROOM *********************************************/

//	@Query(value = "SELECT * FROM admission a, bill b WHERE a.id = b.admission_id AND a.facility_id =:facilityId AND a.admission_status = 'B' AND  b.bill_status = 'C' AND a.pratician_id =:practicianID OR a.type = 'emergency' AND a.take_care =:takeCare GROUP by a.id", nativeQuery = true)
	@Query(value = "SELECT * FROM admission a, bill b WHERE a.facility_id =:facilityId AND a.pratician_id =:practicianID AND a.take_care =:takeCare AND (( a.id = b.admission_id AND a.admission_status = 'B' AND  b.bill_status = 'C') OR a.type = 'emergency') GROUP by a.id", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueue(@Param("takeCare") Boolean takeCare, @Param("facilityId") String facilityId,@Param("practicianID") Long practicianID, Pageable pageable);
	
	@Query(value = "SELECT * FROM admission a, bill b WHERE a.id = b.admission_id AND a.facility_id =:facilityId AND a.take_care =:takeCare AND a.admission_status = 'B' AND  b.bill_status = 'C' AND a.take_care =:takeCare GROUP by a.id", nativeQuery = true)
	public Page<Admission> findAllAdmissionsInQueue(@Param("takeCare") Boolean takeCare,@Param("facilityId") String facilityId, Pageable pageable);
	

	@Query(value = "SELECT * FROM admission a, bill b, payment p, pratician pr, service s  WHERE a.id= b.admission_id AND a.take_care =:takeCare and b.id = p.bill_id and a.service_id = pr.speciality_id and s.waiting_room_id = :waiting_room and a.admission_status = 'B' and b.bill_status = 'C' and a.created_at between :fromDate and :toDate and admission_end_date is null GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionInQueueByDate(@Param("takeCare") Boolean takeCare, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);
	
}
