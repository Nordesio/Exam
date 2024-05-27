#include "SortedAirplane.h"
#include<vector>
#include<iostream>

void SortedAirplane :: addAirplane(Airplane airplane) {
	auto it = findPlace(airplane);
	airplanes.insert(it, airplane);
}
void SortedAirplane:: printList() {
	for (auto airplane : airplanes) {
		std::cout << "Model " << airplane.GetModel()
			<< " Number " << airplane.GetPlaneNumber() << std::endl;
	}
}



std::vector<Airplane>::iterator SortedAirplane::findPlace(Airplane airplane) {
	return std::lower_bound(airplanes.begin(), airplanes.end(), airplane,
		[](Airplane a, Airplane b) {
			return a.GetPlaneNumber() < b.GetPlaneNumber();
		});
}

