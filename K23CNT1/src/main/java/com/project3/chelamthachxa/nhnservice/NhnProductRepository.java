package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhndto.NhnProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhnProductRepository extends JpaRepository<NhnProduct, Long> {
    // Additional query methods can be defined here if needed
}
