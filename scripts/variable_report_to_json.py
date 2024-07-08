import argparse
import dataclasses
import pandas as pd
import json

from enum import StrEnum
from typing import List

class Program(StrEnum):
    RADXUP = "RADx-UP"
    RADXRAD = "RADx-rad"
    RADXTECH = "RADx-Tech"
    DHT = "Digital Health Technologies"

class Category(StrEnum):
    ORIG = "orig"
    TRANSFORM = "transform"

class SheetName(StrEnum):
    VARIABLES = "Variable by Study and File"

class ColumnName(StrEnum):
    FILENAME = "File Name"
    PROGRAM = "RADx Program"
    PHSID = "PHS ID"
    VARAIBLES = "Variables"

@dataclasses.dataclass
class DataFile:
    """
    These are properties that are assumed to be readily available
    for each data file in the Data Hub.
    """
    filename: str
    program: Program
    study_id: str
    category: Category
    variables: List[str]

if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="A script that formats variable data to compute harmonization metrics."
    )
    parser.add_argument("-i", "--input", type=str, required=True, help="input file to process")
    parser.add_argument("-o", "--output", type=str, required=True, help="name of output JSON")
    args = parser.parse_args()

    variable_report_xls = pd.ExcelFile(args.input)
    variable_report = pd.read_excel(variable_report_xls, SheetName.VARIABLES)

    datafiles = []
    for index, row in variable_report.iterrows():
        filename = row[ColumnName.FILENAME]
        program = Program(row[ColumnName.PROGRAM])
        study_id = row[ColumnName.PHSID]
        raw_headers = row[ColumnName.VARAIBLES]
        if "transform" in filename:
            category = Category.TRANSFORM
        else:
            category = Category.ORIG
        headers = [x.strip() for x in raw_headers.split(",")]

        datafile = DataFile(filename, program, study_id, category, headers)
        datafiles.append(dataclasses.asdict(datafile))

    with open(args.output, "w") as output:
        json.dump(datafiles, output, indent=2)
