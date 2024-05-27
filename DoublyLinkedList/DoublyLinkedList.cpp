#include <iostream>
#include <string>

class DoublyLinkedList {
private:

	struct Node {
		int idNode;

		Node* next;
		Node* prev;

		Node(int id) :
			idNode(id), prev(nullptr), next(nullptr) {}

	};

	Node* head;
	Node* tail;

public:
	DoublyLinkedList() : head(nullptr), tail(nullptr) {};

	void addNode(int idNode) {
		Node* newNode = new Node(idNode);
		if (!head) {
			head = tail = newNode;
		}
		else {
			Node* current = head;
			Node* previous = nullptr;
			while (current && current->idNode < idNode) {
				previous = current;
				current = current->next;
			}
			if (!previous) {
				newNode->next = head;
				head->prev = newNode;
				head = newNode;
			}
			else {
				newNode->next = previous->next;
				newNode->prev = previous;
				previous->next = newNode;
				if (current) {
					current->prev = newNode;
				}
				else {
					tail = newNode;
				}
			}
		}
	}

	void print() const {
		Node* current = head;
		while (current) {
			std::cout << " " << current->idNode;
			current = current->next;
		}
		std::cout << std::endl;
	}


	void insert() {
		char choice;
		do {
			int idNode;
			std::cout << "Number:";
			std::cin >> idNode;
			std::cin.ignore();

			addNode(idNode);

			std::cout << "Next?:(y/n)";
			std::cin >> choice;
			print();

		} while (choice == 'y');

	}

	~DoublyLinkedList() {
		Node* current = head;
		while (current) {
			Node* temp = current;
			current = current->next;
			delete temp;
		}
	}
};

int main() {
	DoublyLinkedList list;
	std::cout << "DoublyLinkedList " << std::endl;
	list.addNode(122);
	list.addNode(120);
	list.addNode(121);
	list.addNode(124);
	list.addNode(1);
	list.print();
	list.insert();
}