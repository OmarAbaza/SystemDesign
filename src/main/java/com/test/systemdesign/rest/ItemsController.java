package com.test.systemdesign.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.systemdesign.entity.ItemType;
import com.test.systemdesign.entity.PermissionLevel;
import com.test.systemdesign.entity.item.File;
import com.test.systemdesign.entity.item.Folder;
import com.test.systemdesign.entity.item.Space;
import com.test.systemdesign.rest.dto.ItemRequest;
import com.test.systemdesign.rest.dto.ItemResponse;
import com.test.systemdesign.rest.dto.ResponseStatusMessage;
import com.test.systemdesign.service.ItemsService;
import com.test.systemdesign.service.PermissionsService;
import com.test.systemdesign.service.exception.ItemNotFoundException;
import com.test.systemdesign.service.exception.PermissionGroupNotFoundException;

@RestController
public class ItemsController {
	@Autowired
	private ItemsService itemsService;

	@Autowired
	private PermissionsService permissionsService;

	// Space management endpoints
	@GetMapping("/items/space")
	public ResponseEntity<ItemResponse> getSpaceByName(@RequestHeader String userId, @RequestParam String name) {
		try {
			if (!permissionsService.permissionLevelGrantedForUserOnItem(name, ItemType.Space, userId,
					PermissionLevel.VIEW)) {
				return new ResponseEntity<ItemResponse>(
						new ItemResponse(false, ResponseStatusMessage.ACCESS_DENIED, null), HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<ItemResponse>(new ItemResponse(true,
					ResponseStatusMessage.SPACE_RETRIEVED_SUCCESSFULLY, itemsService.getSpaceByName(name)),
					HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.SPACE_NOT_FOUND, null), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/items/space")
	public ResponseEntity<ItemResponse> createSpace(@RequestBody ItemRequest requestDTO) {
		try {
			Space createdSpace = itemsService.createSpace(requestDTO.getName(), requestDTO.getPermissionGroupName());
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(true, ResponseStatusMessage.SPACE_CREATED_SUCCESSFULLY, createdSpace),
					HttpStatus.CREATED);
		} catch (PermissionGroupNotFoundException permissionGroupNotFoundException) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.PERMISSION_GROUP_NAME_INVALID, null),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.TECHNICAL_FAILURE, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Folder management endpoints
	@GetMapping("/items/folder")
	public ResponseEntity<ItemResponse> getFolderByName(@RequestHeader String userId, @RequestParam String name) {
		try {
			if (!permissionsService.permissionLevelGrantedForUserOnItem(name, ItemType.Folder, userId,
					PermissionLevel.VIEW)) {
				return new ResponseEntity<ItemResponse>(
						new ItemResponse(false, ResponseStatusMessage.ACCESS_DENIED, null), HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<ItemResponse>(new ItemResponse(true,
					ResponseStatusMessage.FOLDER_RETRIEVED_SUCCESSFULLY, itemsService.getFolderByName(name)),
					HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.FOLDER_NOT_FOUND, null), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/items/folder")
	public ResponseEntity<ItemResponse> createFolder(@RequestHeader String userId,
			@RequestBody ItemRequest requestDTO) {
		try {
			if (!permissionsService.permissionLevelGrantedForUserOnItem(requestDTO.getParentName(),
					requestDTO.getParentType(), userId, PermissionLevel.EDIT)) {
				return new ResponseEntity<ItemResponse>(
						new ItemResponse(false, ResponseStatusMessage.ACCESS_DENIED, null), HttpStatus.FORBIDDEN);
			}
			Folder createdFolder = itemsService.createFolder(requestDTO.getName(), requestDTO.getPermissionGroupName(),
					requestDTO.getParentName(), requestDTO.getParentType());
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(true, ResponseStatusMessage.FOLDER_CREATED_SUCCESSFULLY, createdFolder),
					HttpStatus.CREATED);
		} catch (PermissionGroupNotFoundException permissionGroupNotFoundException) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.PERMISSION_GROUP_NAME_INVALID, null),
					HttpStatus.BAD_REQUEST);
		} catch (ItemNotFoundException itemNotFoundException) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.PARENT_NOT_FOUND, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.TECHNICAL_FAILURE, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// File endpoints
	@GetMapping("/items/file")
	public ResponseEntity<ItemResponse> getFileByName(@RequestHeader String userId, @RequestParam String name) {
		try {
			if (!permissionsService.permissionLevelGrantedForUserOnItem(name, ItemType.File, userId,
					PermissionLevel.VIEW)) {
				return new ResponseEntity<ItemResponse>(
						new ItemResponse(false, ResponseStatusMessage.ACCESS_DENIED, null), HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<ItemResponse>(new ItemResponse(true,
					ResponseStatusMessage.FILE_RETRIEVED_SUCCESSFULLY, itemsService.getFileByName(name)),
					HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.SPACE_NOT_FOUND, null), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/items/file/download")
	public ResponseEntity<Resource> downloadFileByName(@RequestHeader String userId, @RequestParam String name) {
		try {
			if (!permissionsService.permissionLevelGrantedForUserOnItem(name, ItemType.File, userId,
					PermissionLevel.VIEW)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			File file = itemsService.getFileByName(name);
			byte[] binaryData = file.getFile().getBinaryData();
			ByteArrayResource resource = new ByteArrayResource(binaryData);

			return ResponseEntity.ok().contentLength(binaryData.length).contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(resource);
		} catch (ItemNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/items/file")
	public ResponseEntity<ItemResponse> createFile(@RequestHeader String userId, @RequestParam String name,
			@RequestParam String permissionGroupName, @RequestParam String parentName,
			@RequestParam ItemType parentType, @RequestParam MultipartFile file) {
		try {
			if (!permissionsService.permissionLevelGrantedForUserOnItem(parentName, parentType, userId,
					PermissionLevel.EDIT)) {
				return new ResponseEntity<ItemResponse>(
						new ItemResponse(false, ResponseStatusMessage.ACCESS_DENIED, null), HttpStatus.FORBIDDEN);
			}
			File createdFile = itemsService.createFile(name, permissionGroupName, parentName, parentType,
					file.getBytes());
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(true, ResponseStatusMessage.FILE_CREATED_SUCCESSFULLY, createdFile),
					HttpStatus.CREATED);
		} catch (PermissionGroupNotFoundException permissionGroupNotFoundException) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.PERMISSION_GROUP_NAME_INVALID, null),
					HttpStatus.BAD_REQUEST);
		} catch (ItemNotFoundException itemNotFoundException) {
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.PARENT_NOT_FOUND, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ItemResponse>(
					new ItemResponse(false, ResponseStatusMessage.TECHNICAL_FAILURE, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
