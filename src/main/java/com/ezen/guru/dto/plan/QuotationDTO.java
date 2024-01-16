package com.ezen.guru.dto.plan;

import com.ezen.guru.domain.Quotation;
import com.ezen.guru.domain.QuotationDetail;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QuotationDTO {
    private String id;
    private String company_id;
    private String company_name;
    private int quotation_totalprice;
    private LocalDateTime regdate;
    private LocalDateTime deadline;
    private int status;
    private String quotation_memo;
    private List<QuotationDetailDTO> quotationDetails;

    public static class QuotationDTOBuilder {
        private String id;
        private String company_id;
        private String company_name;
        private int quotation_totalprice;
        private LocalDateTime regdate;
        private LocalDateTime deadline;
        private int status;
        private String quotation_memo;
        private List<QuotationDetailDTO> quotationDetails;

        QuotationDTOBuilder() {
        }

        public QuotationDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public QuotationDTOBuilder company_id(String company_id) {
            this.company_id = company_id;
            return this;
        }

        public QuotationDTOBuilder company_name(String company_name) {
            this.company_name = company_name;
            return this;
        }

        public QuotationDTOBuilder quotation_totalprice(int quotation_totalprice) {
            this.quotation_totalprice = quotation_totalprice;
            return this;
        }

        public QuotationDTOBuilder regdate(LocalDateTime regdate) {
            this.regdate = regdate;
            return this;
        }

        public QuotationDTOBuilder deadline(LocalDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public QuotationDTOBuilder status(int status) {
            this.status = status;
            return this;
        }

        public QuotationDTOBuilder quotation_memo(String quotation_memo) {
            this.quotation_memo = quotation_memo;
            return this;
        }

        public QuotationDTOBuilder quotationDetails(List<QuotationDetailDTO> quotationDetails) {
            this.quotationDetails = quotationDetails;
            return this;
        }

        public QuotationDTO build() {
            return new QuotationDTO(
                    id, company_id, company_name, quotation_totalprice,
                    regdate, deadline, status, quotation_memo, quotationDetails
            );
        }
    }
}


