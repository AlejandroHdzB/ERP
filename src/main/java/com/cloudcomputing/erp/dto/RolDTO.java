package com.cloudcomputing.erp.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class RolDTO {

    @SerializedName("_id")
    private String id;

    @SerializedName("rol")
    private String rol;

    @SerializedName("estatus")
    private String estatus;
}
