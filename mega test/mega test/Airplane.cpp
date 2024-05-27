#include "Airplane.h"
#include<string>

Airplane::Airplane() : model(""), planeNumber("") {}
Airplane::Airplane( std::string model,  std::string planeNumber) :
	model(model), planeNumber(planeNumber) {}

std::string Airplane::GetModel() {
	return model;
}
std::string Airplane::GetPlaneNumber()  {
	return planeNumber;
}

void Airplane::SetModel( std::string model) {
	this->model = model;
}

void Airplane::SetPlaneNumber( std::string planeNumber) {
	this->planeNumber = planeNumber;
}


